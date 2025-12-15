package com.cdsoftware.docuware.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.compiere.model.IArchiveStore;
import org.compiere.model.MArchive;
import org.compiere.model.MStorageProvider;
import org.compiere.util.CLogger;
import org.compiere.util.Util;

import com.cdsoftware.docuware.util.DMSConfig;
import com.cdsoftware.docuware.util.DocuwareUtil;

public class ArchiveDocuware implements IArchiveStore{

	private final CLogger log = CLogger.getCLogger(getClass());
	@Override
	public byte[] loadLOBData(MArchive archive, MStorageProvider prov) {
	    DMSConfig config = DocuwareUtil.getDMSConfig(prov, archive.getAD_Table_ID(), archive.getRecord_ID());
	    
	    try {
	    	String session = DocuwareUtil.loginDMS(config);
	        
	        if (session != null) {
	            log.severe("Login Exitoso");
	            // lógica para obtener el documento desde Docuware usando el ID almacenado en archive
	            String uuid = new String(archive.getByteData()).trim();
	            if (!uuid.isEmpty()) {
	                return DocuwareUtil.getDocument(uuid, config, session);
	            }
	        } else {
	            log.severe("Login Fallido a Docuware");
	        }
	    } catch (Exception e) {
	        log.log(Level.SEVERE, "Error al cargar datos desde Docuware", e);
	    }
	    
	    // Fallback: retornar los datos almacenados localmente
	    return archive.getByteData();
	}

	@Override
	public void save(MArchive archive, MStorageProvider prov, byte[] inflatedData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteArchive(MArchive archive, MStorageProvider prov) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPendingFlush() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void flush(MArchive archive, MStorageProvider prov) {
		// TODO Auto-generated method stub
		
	}

}
