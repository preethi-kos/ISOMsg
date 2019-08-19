package com.example.pack;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class PackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackApplication.class, args);
		byte[] encoded=parseISOMsg();
		unparse(encoded);
	}
	
	public static byte[] parseISOMsg()
	{
		System.out.println("in parse msg");
		GenericPackager packager;
		byte[] data=null;
		try {
			packager = new GenericPackager("src/main/resources/iso93binary.xml");
		
		// Create ISO Message
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.setMTI("0200");
		isoMsg.set(3, "201234");
		isoMsg.set(4, "10000");
		isoMsg.set(7, "110722180");
		isoMsg.set(11, "123456");
		isoMsg.set(44, "A5DFGR");
		isoMsg.set(105, "VETEALDEMONI");

		// print the DE list
		System.out.println(isoMsg);

		// Get and print the output result
		data = isoMsg.pack();
		System.out.println("RESULT : " + new String(data));
		
		} catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		
	}
	
	public static void unparse(byte[] encoded)
	{
		System.out.println("in unparse msg");
		try {
		GenericPackager packager = new GenericPackager("src/main/resources/iso93binary.xml");
		
		// Print Input Data
		String data = "0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR031VETEALDEMONIOISO8583 1234567890";
		System.out.println("DATA : " + data);

		// Create ISO Message
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.unpack(encoded);

		// print the DE list
		System.out.println(isoMsg);
		System.out.println("iso children"+isoMsg.getChildren());
		System.out.println("  MTI : " + isoMsg.getMTI());
		for (int i=1;i<=isoMsg.getMaxField();i++) {
			if (isoMsg.hasField(i)) {
				System.out.println("    Field-"+i+" : "+isoMsg.getString(i));
			}
		}
		}
		catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
