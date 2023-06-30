package com.multi.quizwiki.mypage.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.multi.quizwiki.mypage.dto.InquryFileDTO;

@Service
public class MypageFileService {
	@Value("${file.dir}")
	private String uploadpath;
	
	//UUID를 이용해서 파일 명을 변경해서 리턴하는 메소드-> 파일명을 변경하는 이유는 겹칠수있어서 
		private String createStoreFilename(String inqury_origin) {
			int pos=inqury_origin.lastIndexOf(".");// 확장자빼고 잘라내려면 위치 알아야 
			String ext = inqury_origin.substring(pos+1);// 시작 인텍스부터 끝까지 문자열 추출
			String uuid=UUID.randomUUID().toString();
			return uuid+"."+ext;
		}
		//업로드와 다운로드를 하면서 필요한 폴더와 파일을 연결한 fullpath를 넘기는 메소드 
		public String getUploadpath(String filename) {
			return uploadpath+filename;
		}

		//파일 한개를 업로드 하고 
		public String uploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
			String inqury_store="";
			if(!multipartFile.isEmpty()) {
				//클라이언트가 업로드한 원본 파일명
				String inqury_origin = multipartFile.getOriginalFilename();
				//서버에서 식별할 수 있도록 파일명을 변경 
				inqury_store= createStoreFilename(inqury_store);
				
				//파일명과 path를 이용해서 실제 파일 객체 만든후 업로드하기
				//xxx/WEB-INF/upload+/+파일명 
				multipartFile.transferTo(new File(getUploadpath(inqury_store)));
				System.out.println("원본파일명=>"+inqury_origin);
				System.out.println("저장파일명=>"+inqury_store);
				}
			return inqury_store;
		}
		public List<InquryFileDTO> uploadFiles(List<MultipartFile> multipartFiles) throws IllegalStateException, IOException {
			List<InquryFileDTO> filedtolist= new ArrayList<InquryFileDTO>();
			int count =1;		
			for (MultipartFile multipartFile : multipartFiles) {
				//업로드를 하는 경우 원본 파일명과 서버에서 식별할 수 있는 실제 서버에서 저장되는 파일명 두개 관리(원본만 하면 파일명 겹칠수있으니까)
				String storeFilename = uploadFile(multipartFile);
				filedtolist.add(new InquryFileDTO(count+"",null, multipartFile.getOriginalFilename(), storeFilename));
				count++;
				}
			
			return filedtolist;
		}
			
}
