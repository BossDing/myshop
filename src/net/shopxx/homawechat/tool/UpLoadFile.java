//package net.shopxx.micro.tool;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//import com.sinocc.base.file.CPCFileLocal;
//import com.sinocc.base.file.FileException;
//import com.sinocc.docmanager.CPCDoc;
//import com.sinocc.exception.SinocpcException;
//import com.sinocc.exception.SystemException;
//import com.sinocc.file.CPCDocFile;
//import com.sinocc.systemdbmanager.CPCDao;
//import com.sinocc.util.CPCLocInfo;
//import com.sinocc.util.GlobalVariable;
//import com.sinocc.util.ModObj;
//import com.sinocc.util.ObjectPath;
//import com.sinocc.util.ObjectType;
//import com.sinocc.util.SysUseObject;
//
///**
// * 文件上传
// * @author xiaolai
// * @version 创建时间：Mar 5, 2014 11:43:41 AM
// * 类说明 
// */
//public class UpLoadFile {
//	public SysUseObject sysObj;
//	public CPCDao dao;
//	public SysUseObject getSysObj() {
//		return sysObj;
//	}
//	public void setSysObj(SysUseObject sysObj) {
//		this.sysObj = sysObj;
//	}
//	public  int createDoc(String account,File tempFile,String mediaId ) throws Exception{
//		//获取上传信息
//		String[] objs=getUpLoadStatus();
//		//第一为文件路径,第二为电子仓ID,第三为存储文件夹ID,第四为fileTime,上传文件时间
//		String TmpFileName = objs[0];
//		String locid = objs[1];
//		String fdrid = objs[2];
//		String fileTime = objs[3];
//		LoadLocs(account);
//
//		//如果未指定文件夹,默认传0
//		if (fdrid == null || fdrid.trim().length() == 0) {
//			fdrid = "0";
//		}
//		InputStream in = new FileInputStream(tempFile);
//		
//		//分段上传
//		int Size = 32768;
//		byte[] bs = new byte[Size];
//		int i = 0;
//		int nSize = 0;
//		int npos = 0;
//		while (true) {
//			nSize = in.read(bs);
//			if (nSize < 0) break;
//			if (nSize > 0) {
//				CPCFileLocal file = new CPCFileLocal();
//				file.setFilePath(TmpFileName);
//				file.setFilePos(npos);
//				file.writeFile(bs, nSize);
//				npos += nSize;
//			}
//		}				
//		CPCDoc doc = new CPCDoc();
//		CPCFileLocal filelocal = new CPCFileLocal();
//		filelocal.setFilePath(TmpFileName);
//		int iLocalCRC32=filelocal.getCRC32();
//		doc.CRC32 = iLocalCRC32;
//		doc.ZipSize = (int) filelocal.getSize();
//		doc.OldSize = doc.ZipSize;
//		doc.IsZiped = 0;
//		doc.DocName = mediaId+".jpg";
//		doc.LocId = 1;
//		doc.TmpFileName = TmpFileName;
//		doc.setParent(this.getSysObj(), this.getDao());
//		String ParentId = ModObj.ATTACH_WSID + "\\";
//		String ParentType = ObjectType.WORK_SPACE + "\\";
//		int isAttach = 2;
//		doc.IsAttach = isAttach;
//		doc.ParentId = ParentId;
//		doc.ParentType = ParentType;
//		doc.setObjPath(new ObjectPath(doc.ParentId, doc.ParentType), true);
//		doc.UpLoadRev = 1;
//		doc.doInsert(doc.EXEC_TYPE_DIRECT, null);
//		this.getSysObj().commitAll();
//		return doc.DocId;
//	}
//	public static String[] getUpLoadStatus() {
//		try {
//			GlobalVariable.log.debug("getUpLoadStatus begin");
//			String tmpFileName = null;
//			
//			int locid = GlobalVariable.dataPool.getDefaultLoc(0, 0);
//	
//			CPCLocInfo loc = GlobalVariable.dataPool.getLocInfo(DaoUtil.EntCode,locid);
//	
//			if (loc == null) {
//	
//				throw new SystemException(20013);
//			}
//	
//			loc.checkFdrPath(new com.sinocc.util.Date());
//	
//			boolean bRemote = loc.isRemote();
//	
//			CPCDocFile docFile = null;
//	
//			com.sinocc.util.Date fileTime = new com.sinocc.util.Date();
//	
//			for (int i = 0; i < 5; i++) {
//	
//				tmpFileName = getTempFileName();
//				docFile = new CPCDocFile(loc, tmpFileName, fileTime);
//	
//				if (docFile.createNewFile()) break;
//			}
//	
//			if (bRemote)
//				tmpFileName = "http||loc="
//						+ loc.getLocId()
//						+ "||"
//						+ docFile.getFileSvrPath();
//			else
//				tmpFileName = docFile.getMainSvrPath();
//	
//			GlobalVariable.log.debug("getUpLoadStatus end");
//			return new String[] { tmpFileName,
//					String.valueOf(locid),
//					null,
//					fileTime.toText1() };
//		}
//		catch (Exception e) {
//			GlobalVariable.log.error(e);
//			return null;
//		}
//	
//	}
//	
//	public static void LoadLocs(String userid){
//			
//			GlobalVariable.log.debug("LoadLocs");
//	 	    String  strEntCode = DaoUtil.EntCode;
//			SysUseObject sysObj = null;
//			try{
//			  sysObj = new SysUseObject(userid, strEntCode);
//			  GlobalVariable.dataPool.loadLocs(strEntCode, sysObj.getDataCentre());
//			}catch(Exception e){
//				GlobalVariable.log.debug(e.getMessage());
//			}finally {
//				if (sysObj != null) {
//					sysObj.freeAllConn();
//				}
//			}
//			
//		}
//
//
//	private static String getTempFileName() {
//		int id = 0;
//		if (GlobalVariable.TempFileId > 99999999)
//			GlobalVariable.TempFileId = 1;
//		id = GlobalVariable.TempFileId;
//		GlobalVariable.TempFileId++;
//		return "~" + String.valueOf(id) + ".tmp";
//	}
//	public CPCDao getDao() {
//		return dao;
//	}
//	public void setDao(CPCDao dao) {
//		this.dao = dao;
//	}
//}
