package com.xingluo.qiniudemo;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xingluo.util.ByteToInputStream;
import com.xingluo.util.RestJson;


@Scope("prototype")
@Controller
@RequestMapping("/qiniu")
public class qiniuController {
	// 配置密匙
	 private Auth auth = Auth.create("jJnfSu9qywVALit_fQWhtjY3XeY2pkTBi7M9tIrF",
	"11vpyByOSLq1Z_PA_rindi3LIlFWM3lXkdovoSr9");
	 //七牛管理类
	 private BucketManager bucketManager = new BucketManager(auth);
	 
	 /**
	 * 根据前缀获取文件列表的迭代器
	 *
	 * @param bucket    空间名
	 * @param prefix    文件名前缀
	 * @param limit     每次迭代的长度限制，最大1000，推荐值 100
	 * @param delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
	 * @return FileInfo迭代器
	 */
	 public  void getfileName(){
		 BucketManager.FileListIterator it = bucketManager.createFileListIterator("normal-goods", null, 100, null);

		 while (it.hasNext()) {
		     FileInfo[] items = it.next();
		     if (items.length > 1) {
		         System.out.println(items[0]);
		     }
		 }
	 }
	 
	 /*
	  * 根据空间名和文件名删除文件
	  */
	 public void deleteFile(String bucket,String key) throws QiniuException{
		bucketManager.delete(bucket, key);
	 }
	 
	 
	/**
	 * 用于七牛js上传时获得uptoken. uptoken可简单理解为七牛上传的凭证，JS在设置的时候需要这个。 by：方宇
	 * 
	 */
	@RequestMapping(params = "uptoken", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> uptoken(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> j = new HashMap<String,Object>();
	
		// 简单上传，使用默认策略
		UUID uuid = UUID.randomUUID();
		String upToken =  auth.uploadToken("normal-goods", null, 3600, new StringMap()
        .putNotEmpty("saveKey", uuid.toString()), true);
		
		j.put("uptoken", upToken);
		j.put("key",DateUtils.yyyyMMdd.format(DateUtils.getDate())+uuid.toString().substring(20, 33));
		return j;
	}

	/**
	 * 七牛上传damo接口 主要用于测试，以后要写上传时看 
	 * 方宇
	 */
	@RequestMapping(params = "updateQiniu", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson updateQiniu(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		
		// 简单上传，使用默认策略
		String upToken = auth.uploadToken("normal-goods");
		// 七牛上传对象
		UploadManager uploadManager = new UploadManager();
		// 用来存放表单中的字段
		Map<String, Object> map = new HashMap<String, Object>();
		// 用来存放保存的文件url
		List<String> list = new ArrayList<String>();
		Map<String, String[]> parMap = null;

		try {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = mRequest.getFileMap();
			parMap = mRequest.getParameterMap();
			int i = 0;
			// 遍历表单中的文件段，并上传到七牛
			for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap
					.entrySet().iterator(); it.hasNext(); i++) {

				Map.Entry<String, MultipartFile> entry = it.next();
				MultipartFile mFile = entry.getValue();
				String  uuid = UUIDGenerator.generate();
				// 文件名 String fileName = item.getName();
				InputStream content = mFile.getInputStream();
				byte[] data = ByteToInputStream.input2byte(content);
				Response res = uploadManager.put(data, uuid, upToken);
				
				//System.out.println(res);
				if (res.isOK()) {
					list.add("http://7xp1b8.com1.z0.glb.clouddn.com/"+ uuid);
				} else {
					j.setMsg("上传出现错误！");
					j.setSuccess(false);
					return j;
				}
			}
			// 遍历表单中的参数字段，将String[]转换出来
			if (parMap != null) {
				Set<Map.Entry<String, String[]>> set = parMap.entrySet();
				Iterator<Map.Entry<String, String[]>> it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<String, String[]> entry = it.next();
					//System.out.println("KEY:" + entry.getKey());
					for (String s : entry.getValue()) {
						map.put(entry.getKey(),s);
					}
				}
			}
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (map.size() > 0 || list.size() > 0) {
			j.setAttributes(map);
			j.setObj(list);
			j.setMsg("上传成功！");
			j.setSuccess(true);
		} else {
			j.setSuccess(false);
			j.setMsg("上传出现错误！");
		}
		return j;
	}

}
