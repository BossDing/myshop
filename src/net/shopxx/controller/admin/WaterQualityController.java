package net.shopxx.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.FileInfo.FileType;
import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;
import net.shopxx.service.AreaService;
import net.shopxx.service.WaterQualityService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller - 水质设置
 *      
 * @author WeiHuaLin
 * @version 1.0
 */
@Controller("waterQualityController")
@RequestMapping("/admin/waterquality")
public class WaterQualityController extends BaseController{
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "waterQualityServiceImpl")
	private WaterQualityService waterQualityService;  

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", waterQualityService.findPage(pageable));
		return "/admin/waterquality/list";    
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		List<Area> areas = new ArrayList<Area>();
		areas = areaService.findRoots();
		model.addAttribute("areas", areas);
		return "/admin/waterquality/add";
	}
	
	/**
	 * 添加Excel
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.GET)
	public String importExcel(ModelMap model) {
		return "/admin/waterquality/importExcel";
	}
	
	/**
	 * Excel导入上传
	 */
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String  uploadExcel(FileType fileType, MultipartFile file, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		List<WaterQualityData> waters=new ArrayList<WaterQualityData>();
		Workbook wb=null;
		try {
			byte[] bytes=file.getBytes();
			File f=new File("excel.xls");
			OutputStream out=new FileOutputStream(f);
			out.write(bytes);
			out.close();
			wb = Workbook.getWorkbook(f);
			Sheet sheet=wb.getSheets()[0];
			int cloums=sheet.getColumns();
			int rows=sheet.getRows();
			String datastr="";
			Cell cell =null;
			for(int i=1;i<rows;i++){
				WaterQualityData water = new WaterQualityData();
				for(int j=0;j<cloums;j++){
					cell=sheet.getCell(j,i);
					datastr=cell.getContents();
					if(j==0){water.setProvinceName(datastr);}
					if(j==1){water.setCityName(datastr);}
					if(j==2){water.setDistrictName(datastr);}
					if(j==3){water.setCommunityName(datastr);}
					if(j==4){water.setTds(Integer.parseInt(datastr));}
					if(j==5){water.setChlorine(Double.parseDouble(datastr));}
					if(j==6){water.setItemSpec(datastr);}
				}
				waters.add(water);
			}
			waters=areaService.setIds(waters);
			for(WaterQualityData w : waters){
				waterQualityService.save(w);
			}
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			addFlashMessage(redirectAttributes, new Message(Message.Type.error,"请确保Excel的数据格式的正确性"));
		} 
		return "redirect:list.jhtml";
	}
	
	
	 @RequestMapping("/download/{fileName}")  
	    public ModelAndView download(@PathVariable("fileName")  
	    String fileName, HttpServletRequest request, HttpServletResponse response)  
	            throws Exception {  
	  
	        response.setContentType("text/html;charset=utf-8");  
	        request.setCharacterEncoding("UTF-8");  
	        java.io.BufferedInputStream bis = null;  
	        java.io.BufferedOutputStream bos = null;  
	  
	        String ctxPath = request.getSession().getServletContext().getRealPath("/") +"\\"+ "resources\\"+"admin\\"+"WaterQualityExcel\\";
	        String downLoadPath = ctxPath + fileName;  
	        ex(downLoadPath,null);
	        try {  
	            long fileLength = new File(downLoadPath).length();  
	            response.setContentType("application/x-msdownload;");  
	            response.setHeader("Content-disposition", "attachment; filename="+fileName);  
	            response.setHeader("Content-Length", String.valueOf(fileLength));  
	            bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	            bos = new BufferedOutputStream(response.getOutputStream());  
	            byte[] buff = new byte[2048];  
	            int bytesRead;  
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	                bos.write(buff, 0, bytesRead);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (bis != null)  
	                bis.close();  
	            if (bos != null)  
	                bos.close();  
	        }  
	        return null;  
	    }  
	
	 @RequestMapping("/download1/{fileName}")  
	    public ModelAndView download1(@PathVariable("fileName") String fileName, 
	    Long[] ids,  HttpServletRequest request, HttpServletResponse response)  
	            throws Exception {  
		 	response.setContentType("text/html;charset=utf-8");  
	        request.setCharacterEncoding("UTF-8");  
	        java.io.BufferedInputStream bis = null;  
	        java.io.BufferedOutputStream bos = null;  
	  
	        String ctxPath = request.getSession().getServletContext().getRealPath("/") +"\\"+ "resources\\"+"admin\\"+"WaterQualityExcel\\";
	        String downLoadPath = ctxPath + fileName;  
	        ex(downLoadPath,ids); 
	        try {  
	            long fileLength = new File(downLoadPath).length();  
	            response.setContentType("application/x-msdownload;");  
	            response.setHeader("Content-disposition", "attachment; filename="+fileName);  
	            response.setHeader("Content-Length", String.valueOf(fileLength));  
	            bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	            bos = new BufferedOutputStream(response.getOutputStream());  
	            byte[] buff = new byte[2048];  
	            int bytesRead;  
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	                bos.write(buff, 0, bytesRead);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (bis != null)  
	                bis.close();  
	            if (bos != null)  
	                bos.close();  
	        }  
	        return null;    
	    }
	 
	/**
	 * 设置推荐
	 */
	@RequestMapping(value = "/setItemSpec", method = RequestMethod.GET)
	public String setItemSpec(ModelMap model) {
		return "/admin/waterquality/edit";
	}
	
	/**
	 * 更新推荐机型
	 */
	@RequestMapping(value = "/updateItmSpec", method = RequestMethod.POST)
	public String updateItmSpec(Long areaId, Long gt_tds,Long ltqt_tds, Double gt_cl, Double ltqt_cl, String itemSpec, RedirectAttributes redirectAttributes) {
		Area area = null;
		if(areaId!=null){
			area=areaService.find(areaId);
		}
		waterQualityService.toUpdateWaterQualityData(area,gt_tds,ltqt_tds,gt_cl,ltqt_cl,itemSpec);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(WaterQualityData waterQualityData,long areaId, RedirectAttributes redirectAttributes) {
		Area area = areaService.find(areaId);
		if(area.getParent()!=null){
			Area parent = area.getParent();
			if(parent.getParent()!=null){
				Area root = parent.getParent();
				waterQualityData.setDistrictId(areaId);
				waterQualityData.setDistrictName(area.getName());
				waterQualityData.setCityId(parent.getId());
				waterQualityData.setCityName(parent.getName());
				waterQualityData.setProvinceId(root.getId());
				waterQualityData.setProvinceName(root.getName());
			}else{
				if(area.getChildren().size()==0){
					waterQualityData.setCityId(parent.getId());
					waterQualityData.setCityName(parent.getName());
					waterQualityData.setDistrictId(areaId);
					waterQualityData.setDistrictName(area.getName());
					waterQualityData.setProvinceId(parent.getId());
					waterQualityData.setProvinceName(parent.getName());
				}else{
					waterQualityData.setCityId(areaId);
					waterQualityData.setCityName(area.getName());
					waterQualityData.setProvinceId(parent.getId());
					waterQualityData.setProvinceName(parent.getName());
				}
			}
		}else{
			waterQualityData.setProvinceId(area.getId());
			waterQualityData.setProvinceName(area.getName());
		}
		waterQualityService.save(waterQualityData);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		waterQualityService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	public void ex(String filePath,Long[] ids){
		String worksheet = "waterQualityDatasDown";//输出的excel文件工作表名
		String[] title = {"省名","市名","区名","小区名称","TDS值","余氯值","机型推荐"};//excel工作表的标题
		OutputStream os=null;
		WritableWorkbook workbook=null;
		try
		{
		os=new FileOutputStream(filePath);
		workbook=Workbook.createWorkbook(os);

		WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表
		jxl.write.Label label;
		for (int i=0; i<title.length; i++)
		{
		//Label(列号,行号 ,内容 )
		label = new jxl.write.Label(i, 0, title[i]); //put the title in row1
		sheet.addCell(label);
		}
		//写入数据
		List<WaterQualityData> list;
		if(ids!=null){
			list= waterQualityService.findList(ids);
		}else{
			list= waterQualityService.findAll();
		}
		Label data0,data1,data2,data3,data6;
		Number data4,data5;
		for(int j=0;j<list.size();j++){
			
				data0=new Label(0,j+1,list.get(j).getProvinceName());
				data1=new Label(1,j+1,list.get(j).getCityName());
				data2=new Label(2,j+1,list.get(j).getDistrictName());
				data3=new Label(3,j+1,list.get(j).getCommunityName());
				data4=new Number(4,j+1,list.get(j).getTds());
				data5=new Number(5,j+1,list.get(j).getChlorine());
				data6=new Label(6,j+1,list.get(j).getItemSpec());
				sheet.addCell(data0);
				sheet.addCell(data1);
				sheet.addCell(data2);
				sheet.addCell(data3);
				sheet.addCell(data4);
				sheet.addCell(data5);
				sheet.addCell(data6);
			
		}
		
		workbook.write();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
