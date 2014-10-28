package cci.cert.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FTPReader extends CERTReader {
	static final Logger LOG = Logger.getLogger(FTPReader.class);

	@Autowired
	XMLService xmlreader;

	CCIProperty props = CCIProperty.getInstance();

	public void load(CertificateDAO dao) {
		FTPClient ftp = new FTPClient();
		setStopped(false);

		while (!isExitflag()) {

			for (String directory : Config.ftpdirs) {
				try {
					ftp.connect(props.getProperty(Config.URL));
					ftp.login(props.getProperty(Config.LOGIN),
							props.getProperty(Config.PSW));

					FTPFile[] files = ftp.listFiles(directory);
					InputStream input;
					Certificate cert = null, checkcert = null;
					String xmltext;
					long start;
					long cert_id;
					int counter = 1;

					long otd_id = dao.getOtdIdBySynonimName(directory);
					LOG.info("ID ���������: " + otd_id);

					// ���� ���� ����� ���������
					if (otd_id > 0) {

						for (FTPFile file : files) {
							if (file.getType() == FTPFile.FILE_TYPE) {

								start = System.currentTimeMillis();

								try {
									LOG.info("������ FTP ����: "
											+ directory
											+ props.getProperty(Config.FTPSEPARATOR)
											+ file.getName());
									input = ftp
											.retrieveFileStream(directory
													+ props.getProperty(Config.FTPSEPARATOR)
													+ file.getName());

									if (input != null) {
										xmltext = getStringFromInputStream(input);

										try {
											cert = xmlreader
													.loadCertificate(new ByteArrayInputStream(
															xmltext.getBytes()));
										} catch (Exception ex) {
											LOG.error("������ �������� �����������: "
													+ ex.toString());
											ex.printStackTrace();
											cert = null;
										}
										input.close();

										if (!ftp.completePendingCommand()) {
											ftp.logout();
											ftp.disconnect();
											LOG.error("File transfer failed.");
										}

										if (cert != null) {
											try {
												cert.setOtd_id((int) otd_id);
												checkcert = dao.check(cert); // ���������
																				// �������
												boolean saved;

												String lfile = props
														.getProperty(Config.REPPATH)
														+ directory
														+ File.separator
														+ file.getName();

												if (checkcert == null) {
													cert_id = dao.save(cert);

													if (cert_id > 0) {
														LOG.info("���������� � ������� "
																+ cert_id
																+ " �������� � ���� ������");
														saved = saveFileIntoL�calDirectory(
																lfile, xmltext);

														if (saved) {
															LOG.info("���� ����������� � ������� "
																	+ cert_id
																	+ " �������� � ��������� ���������");
															dao.saveFile(
																	cert_id,
																	lfile);

															if (Boolean
																	.parseBoolean(props
																			.getProperty(Config.ISDELETE))) {
																ftp.deleteFile(directory
																		+ props.getProperty(Config.FTPSEPARATOR)
																		+ file.getName());
																LOG.info("���� "
																		+ directory
																		+ props.getProperty(Config.FTPSEPARATOR)
																		+ file.getName()
																		+ " ������ � FTP");
															}
														}
													} else {
														LOG.info("���������� � �������  "
																+ cert_id
																+ " �� ����� ���� �������� � ���� ������. ������ �������� �����������...");
													}
												} else {
													if (!checkcert.equals(cert)) {
														LOG.info("����������c ������� "
																+ cert.getNomercert()
																+ " ���������. ����������� ����������... ");
														cert_id = checkcert
																.getCert_id();
														cert.setCert_id(cert_id);
														dao.update(cert);

														saved = saveFileIntoL�calDirectory(
																lfile, xmltext);

														if (saved) {
															dao.saveFile(
																	cert_id,
																	lfile);
														}
													} else {
														LOG.info("���������� c ������� "
																+ cert.getNomercert()
																+ " ��� ���������������. �������... ");
													}

													if (Boolean
															.parseBoolean(props
																	.getProperty(Config.ISDELETE))) {
														ftp.deleteFile(directory
																+ props.getProperty(Config.FTPSEPARATOR)
																+ file.getName());

														LOG.info("���� "
																+ directory
																+ props.getProperty(Config.FTPSEPARATOR)
																+ file.getName()
																+ " ������ � FTP �������");
													}
												}

											} catch (Exception ex) {
												LOG.error("������ ���������� �����������: "
														+ ex.toString());
												ex.printStackTrace();
											}
										}
									} else {
										LOG.info("FTP Input �� ��� ������");
									}
								} catch (Exception ex) {
									LOG.error("���������� �� �������� ��-�� ������: "
											+ ex.toString());
									ex.printStackTrace();
								}
								LOG.info("����� �������� ����������� �� ����� "
										+ directory
										+ props.getProperty(Config.FTPSEPARATOR)
										+ file.getName() + " ���������: "
										+ (System.currentTimeMillis() - start));
							}
							if (++counter > Integer.parseInt(props
									.getProperty(Config.FILES)) || isExitflag()) {
								break;
							}
						}
					}
					ftp.logout();
					ftp.disconnect();

				} catch (SocketException e) {
					LOG.error("������ �� ������ ������ ������");
					e.printStackTrace();
				} catch (IOException e) {
					LOG.error("������ �����-������");
					e.printStackTrace();
				}

				if (isExitflag()) {
					break;
				}
			}

			if (isExitflag()) {
				break;
			}

			try {
				LOG.info("����� � ������ FTP �������");
				Thread.sleep(Integer.parseInt(props.getProperty(Config.DELAY)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		setStopped(true);
		LOG.info("FTPReader finished");
	}

}
