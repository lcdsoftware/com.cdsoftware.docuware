package com.cdsoftware.docuware.model;

import org.compiere.model.IAttachmentStore;
import org.compiere.model.MAttachment;
import org.compiere.model.MStorageProvider;
import org.compiere.util.CLogger;

public class AttachmentDocuware implements IAttachmentStore{

	private final CLogger log = CLogger.getCLogger(getClass());
	@Override
	public boolean loadLOBData(MAttachment attach, MStorageProvider prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(MAttachment attach, MStorageProvider prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MAttachment attach, MStorageProvider prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEntry(MAttachment mAttachment, MStorageProvider provider, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
