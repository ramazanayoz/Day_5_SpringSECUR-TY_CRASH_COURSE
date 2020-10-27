package com.example.demo.security;

//ROLES & PERMISSIONS USING ENUMS
public enum ApplicationUserPermissionEnum {
	
	//ENUM OBJECTS CREATED //OLUŞTURULAN ENUM OBJELER
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	//ENUM VERİABLES
	private final String permission;
	
	//ENUM CONST
	 ApplicationUserPermissionEnum(String permission) {
		this.permission = permission;
	}
	 
	//ENUM METHODS
	public String getPermission() {
		return permission;
	}
}
