package com.cos.jwt.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwt.domain.pt.Pt;
import com.cos.jwt.domain.pt.PtRepository;
import com.cos.jwt.domain.review.PtReview;
import com.cos.jwt.domain.review.PtReviewRepository;
import com.cos.jwt.domain.user.User;
import com.cos.jwt.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final PtReviewRepository ptReviewRepository;
	private final PtRepository ptRepository;
	private final UserRepository userRepository;
	
	private String getFolder() {
	    SimpleDateFormat uploadDate = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String str = uploadDate.format(date);
	    
	    return str.replace("-", File.separator);
	}
	
	@Transactional
	public void reviewWrite(HttpServletRequest request, int no, User user, String type, String title, String content, MultipartFile rev_img) {
		User userEntity = userRepository.findById(user.getUserNo()).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
		
		if(type.equals("pt")) {
			Pt pt = ptRepository.findById(no).orElseThrow(() -> new IllegalArgumentException(no + "번의 피티는 없습니다."));
			
			try {		
				
				String uploadFolder = getFolder();
				String uploadFolderPath = request.getServletContext().getRealPath("/pt-app/public/review");
				
				File realUploadFolderPath = new File(uploadFolderPath, uploadFolder);
				if(!realUploadFolderPath.exists()) {
					realUploadFolderPath.mkdirs();
				}
				
				String filename = "";
				
				UUID uuid = UUID.randomUUID(); 
				String strPt_img = rev_img.getOriginalFilename();
				String uploadFilename = uuid.toString() + "_" + strPt_img; 
				String realPath = realUploadFolderPath + "\\" + uploadFilename;
				rev_img.transferTo(new File(realPath));	
				filename = "http://10.100.102.27:8000/images/" + uploadFolder.replace("\\", "/") + "/" + uploadFilename;
				PtReview ptReview = PtReview.builder().user(userEntity).pt(pt).pt_rev_title(title).pt_rev_content(content).pt_rev_img_name(filename).build();
				ptReviewRepository.save(ptReview);
					
			} catch (Exception e) {
				e.getMessage();
			}
			
		} else {
			PtReview ptReview = new PtReview();
		}
	}
}
