package com.cos.jwt.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.pt.PtRepository;
import com.cos.jwt.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PtService {
	
	private final PtRepository ptRepository;
	
	@Transactional
	public void ptWrite(HttpServletRequest request, User trainer, String pt_name, String pt_content, String pt_address, int pt_price, MultipartFile pt_img) {
		try {		
			
			String uploadFolder = getFolder();
			String uploadFolderPath = request.getServletContext().getRealPath("/pt-app/public/");
			
			File realUploadFolderPath = new File(uploadFolderPath, uploadFolder);
			if(!realUploadFolderPath.exists()) {
				realUploadFolderPath.mkdirs();
			}
			
			String filename = "";
			
			UUID uuid = UUID.randomUUID(); 
			String strPt_img = pt_img.getOriginalFilename();
			String uploadFilename = uuid.toString() + "_" + strPt_img; 
			String realPath = realUploadFolderPath + "\\" + uploadFilename;
			pt_img.transferTo(new File(realPath));	
			filename = "http://10.100.102.27:8000/images/" + uploadFolder.replace("\\", "/") + "/" + uploadFilename;
			System.out.println(filename);
			Pt pt = Pt.builder().user(trainer).pt_name(pt_name).pt_content(pt_content).ptAddress(pt_address).pt_price(pt_price).pt_img(filename).build();
			ptRepository.save(pt);

		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	private String getFolder() {
	    SimpleDateFormat uploadDate = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String str = uploadDate.format(date);
	    
	    return str.replace("-", File.separator);
	}
	
	
	@Transactional
	public Page<Pt> ptList(Pageable pageable) {
		return ptRepository.findAll(pageable);
	}
	
	@Transactional
	public Pt ptDetail(int no) {
		return ptRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 게시물은 없습니다."));
	}
	
	@Transactional
	public int ptRemove(User trainer, int no) {
		Pt pt = ptRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 피티 게시물은 없습니다."));
		if(pt.getUser().getId().equals(trainer.getId())) {
			ptRepository.deleteById(no);
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public int ptModify(User trainer, int no, String pt_name, String pt_content, int pt_price, MultipartFile pt_img) {
		Pt pt = ptRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번 피티 게시물은 없습니다."));
		
		if(pt.getUser().getId().equals(trainer.getId())) {
			pt.setPt_name(pt_name);
			pt.setPt_content(pt_content);
			pt.setPt_price(pt_price);
			try {
				String path = "E:\\workspace\\moonjoowan\\ptnprofile-project\\src\\main\\webapp\\pt-app\\public\\";
				String old_pt_img = path + pt.getPt_img().substring(3);
				UUID uuid = UUID.randomUUID();
				String new_pt_img = uuid.toString() + "_" + pt_img.getOriginalFilename();
				
				String uploadFolder = getFolder();
				File folder = new File(path, uploadFolder);
				if(!folder.exists()) {
					folder.mkdirs();
				}
				
				pt_img.transferTo(new File(folder + new_pt_img));
				pt.setPt_img("../" + folder + new_pt_img);
				File old = new File(old_pt_img);
				if(old.exists()) {
					if(old.delete()) {
						System.out.println("파일 삭제 성공!!");
					} else {
						System.out.println("파일 삭제 실패!!");
					}
				} else {
					System.out.println("파일이 존재하지 않습니다.");
				}
			} catch (Exception e) {
				e.getMessage();
			}
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public List<Pt> searchList(String search) {
		List<Pt> ptSearchList = ptRepository.findByPtAddressContaining(search);
		
		return ptSearchList;
	}
	
} 
