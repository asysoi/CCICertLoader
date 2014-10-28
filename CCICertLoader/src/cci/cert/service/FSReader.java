package cci.cert.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cci.cert.certificate.CCIProperty;
import cci.cert.certificate.Config;
import cci.cert.model.Certificate;
import cci.cert.repository.CertificateDAO;
import cci.cert.util.XMLService;

@Component
public class FSReader extends CERTReader {
	static final Logger LOG = Logger.getLogger(FTPReader.class);

	@Autowired
	XMLService xmlreader;

	CCIProperty props = CCIProperty.getInstance();

	public void load(CertificateDAO dao) {
		setStopped(false);

		while (!isExitflag()) {

			for (String directory : Config.ftpdirs) {
				try {

					Certificate cert = null;
					Certificate checkcert = null;
					long start;
					long cert_id;
					int counter = 1;

					String[] files = new File(props.getProperty(Config.XMLPATH)
							+ directory).list();
					long otd_id = dao.getOtdIdBySynonimName(directory);
					LOG.info("ID ��������� " + directory + " : " + otd_id);

					if (otd_id > 0) {
						LOG.info("������� ������: " + files.length);

						for (String filename : files) {
							start = System.currentTimeMillis();

							try {
								String fullfilename = props
										.getProperty(Config.XMLPATH)
										+ directory + File.separator + filename;
								// + FileSystems.getDefault().getSeparator()

								LOG.info("������ ����: " + fullfilename);

								try {
									cert = xmlreader
											.loadCertificate(fullfilename);
									LOG.info("    ����� �������� � �������� ����� ���������: " + 
											+ (System.currentTimeMillis() - start));
									
								} catch (Exception ex) {
									ex.printStackTrace();
								}

								if (cert != null) {
									try {
										cert.setOtd_id((int) otd_id);
										checkcert = dao.check(cert);

										boolean saved;

										String lfile = props
												.getProperty(Config.REPPATH)
												+ directory
												+ File.separator
												+ filename;

										if (checkcert == null) {
											cert_id = dao.save(cert);

											if (cert_id > 0) {
												LOG.info("    ���������� � ������� "
														+ cert_id
														+ " �������� � ���� ������");
												saved = copyFile(fullfilename,
														lfile);

												if (saved) {
													LOG.info("    ���� ����������� � ������� "
															+ cert_id
															+ " �������� � ��������� ���������");
													dao.saveFile(cert_id, lfile);

													if (Boolean
															.parseBoolean(props
																	.getProperty(Config.ISDELETE))) {
														deleteFile(fullfilename);
													}
												}
											} else {
												LOG.info("    ���������� � �������  "
														+ cert_id
														+ " �� ����� ���� �������� � ���� ������. ������ �������� �����������...");
											}
										} else {
											if (!checkcert.equals(cert)) {
												LOG.info("    ����������c ������� "
														+ cert.getNomercert()
														+ " ���������. ����������� ����������... ");
												cert_id = checkcert
														.getCert_id();
												cert.setCert_id(cert_id);
												dao.update(cert);
												LOG.info("    ����� ���������� ���������: " + 
														+ (System.currentTimeMillis() - start));


												saved = copyFile(fullfilename,
														lfile);

												if (saved) {
													dao.saveFile(cert_id, lfile);
												}
											} else {
												LOG.info("    ���������� c ������� "
														+ cert.getNomercert()
														+ " ��� ���������������. �������... ");
											}

											if (Boolean
													.parseBoolean(props
															.getProperty(Config.ISDELETE))) {
												deleteFile(fullfilename);

											}
										}

									} catch (Exception ex) {
										LOG.error("    ������ ���������� �����������: "
												+ ex.toString());
										ex.printStackTrace();
									}
								}
							} catch (Exception ex) {
								LOG.error("    ���������� �� �������� ��-�� ������: "
										+ ex.toString());
								ex.printStackTrace();
							}
							LOG.info("    ��������� ����� �������� ����������� �� ����� "
									+ directory
									+ props.getProperty(Config.FTPSEPARATOR)
									+ filename + " ���������: "
									+ (System.currentTimeMillis() - start));

							if (++counter > Integer.parseInt(props
									.getProperty(Config.FILES)) || isExitflag()) {
								break;
							}
						}
					}
				} catch (Exception e) {
					LOG.error("������: " + e.getMessage());
				}

				if (isExitflag()) {
					break;
				}
			}

			if (isExitflag()) {
				break;
			}

			try {
				LOG.info("����� � ������ ������������");
				Thread.sleep(Integer.parseInt(props.getProperty(Config.DELAY)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		setStopped(true);
		LOG.info("FTPReader finished");
	}

	// -------------------------------------------------------
	// File copy
	// -------------------------------------------------------
	private boolean copyFile(String fullfilename, String lfile)
			throws Exception {
		boolean saved = false;
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		try {
			sourceChannel = new FileInputStream(new File(fullfilename))
					.getChannel();
			destChannel = new FileOutputStream(new File(lfile)).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			saved = true;
		} finally {
			sourceChannel.close();
			destChannel.close();
		}

		return saved;
	}

	// -------------------------------------------------------
	// File delete
	// -------------------------------------------------------
	private void deleteFile(String fullfilename) {
		if ((new File(fullfilename)).delete()) {
			LOG.info("���� " + fullfilename + " ������");
		} else {
			LOG.info("���� " + fullfilename + " �� ������");
		}
	}

}
