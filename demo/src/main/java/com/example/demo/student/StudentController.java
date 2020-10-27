package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students") //http://localhost:8080/api/v1/students/.... adresine req edildiğinde class içi çalışır
public class StudentController {
	
	///http://localhost:8080/api/v1/students/.... adresine req edildiğinde class içi çalıştırılıyor
	private static final List<StudentModel> STUDENTSLİST = Arrays.asList( //demo için bir  student list oluşturduk
		new StudentModel(1,"James Bond"),
		new StudentModel(2, "Maria Jones"),
		new StudentModel(3, "Anna Smith")
	);
	

	
	@GetMapping(path= "{studentId}") //http://localhost:8080/api/v1/students/221 adresine req yapıldığında bu kısım çalışır
	public StudentModel getStudent(@PathVariable("studentId") Integer studentId) {  //@PathVariable ve @GetMapping(path= "{studentId}") sayesinde studentId yerine geçen değer alınır. Bu örnekte Integer studentId değeri 221 olur 
		return STUDENTSLİST.stream() //LİSTE stream ediliiyor		 
				.filter(student -> studentId.equals(student.getStudentId())) //filtreleme yapılıyor
				.findFirst()	//OUTPUT ::: {"studentId":2,"studentName":"Maria Jones"} //filtreleme yapılan listeden ilkini alır
				.orElseThrow( () -> new IllegalStateException("Student "+ studentId+ " does not exist" ));
	}


}
