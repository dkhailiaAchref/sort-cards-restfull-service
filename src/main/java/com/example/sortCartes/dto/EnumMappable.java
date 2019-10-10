package com.example.sortCartes.dto;

public interface EnumMappable {
	public static EnumMappable getEnumFromCode(Integer code, Class<? extends Enum<?>> enumClass) {
		for (Enum<?> type : enumClass.getEnumConstants()) {
			Integer i = ((EnumMappable) type).getCode();

			if (i != null && code != null && i.intValue() == code.intValue()) {
				return (EnumMappable) type;
			}
		}
		return null;
	}

	public static String getLibelleFromCode(Integer code, Class<? extends Enum<?>> enumClass) {
		EnumMappable enumMappable = getEnumFromCode(code, enumClass);
		return enumMappable != null ? enumMappable.getLibelle() : "";
	}

	public Integer getCode();

	public String getLibelle();
}