package fr.maxlego08.worldprotection.zcore.enums;

public enum Folder {

	UTILS,

	;
	

	public String toFolder(){
		return name().toLowerCase();
	}
	
}
