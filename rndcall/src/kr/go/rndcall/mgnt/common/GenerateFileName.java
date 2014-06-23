package kr.go.rndcall.mgnt.common;

import java.util.*;

public class GenerateFileName {

    public GenerateFileName() {
    }

    public static String getFileName(String filename, String username) {
        String result = null;
        StringTokenizer token = new StringTokenizer(filename, ".");
        int tokenSize = token.countTokens();
        if (tokenSize == 1) {
            String file = token.nextToken();
            // 로컬 파일명이 한글인 경우, 서버에 저장되는 파일명에 한글이 포함되는 경우를 방지 (2007-10-26 김종송)
            result =  /* file + "-" + */ username + "-" + getTimeset();
        } else if (tokenSize == 2) {
            String file = token.nextToken();
            String ext = token.nextToken();
            // 로컬 파일명이 한글인 경우, 서버에 저장되는 파일명에 한글이 포함되는 경우를 방지 (2007-10-26 김종송)
            result = /* file + "-" + */ username + "-" + getTimeset() /* + "." + ext */;
        } else {
            StringBuffer file = new StringBuffer();
            for (int i = 0; i < tokenSize - 2; i++) {
                file.append(token.nextToken() + ".");
            }
            file.append(token.nextToken());
            String ext = token.nextToken();
            // 로컬 파일명이 한글인 경우, 서버에 저장되는 파일명에 한글이 포함되는 경우를 방지 (2007-10-26 김종송)
            result = /* file + "-" + */ username + "-" + getTimeset() /* + "." + ext */;
        }
        return result;
    }

    public static String getSourceFileName(String filename) {
        String result = null;
        StringTokenizer token = new StringTokenizer(filename, ".");
        int tokenSize = token.countTokens();
        // System.out.println(tokenSize);
        if (tokenSize == 1) {
            String file = findFileName(token.nextToken());
            result = file;
        } else {
            StringBuffer file = new StringBuffer();
            for (int i = 0; i < tokenSize - 2; i++) {
                file.append(token.nextToken() + ".");
            }
            file.append(findFileName(token.nextToken()));
            String ext = token.nextToken();
            result = file + "." + ext;
        }
        return result;
    }

    private static String findFileName(String filename) {
        return new StringTokenizer(filename, "-").nextToken();
    }

    /** 년월일-시분초 생성 */
    private static String getTimeset() {
        Calendar cal = Calendar.getInstance();
        StringBuffer result = new StringBuffer();
        result.append(cal.get(Calendar.YEAR));

        if ( (cal.get(Calendar.MONTH) + 1) < 10) {
            result.append("0" + (cal.get(Calendar.MONTH) + 1));
        } else {
            result.append(cal.get(Calendar.MONTH) + 1);
        }
        if (cal.get(Calendar.DATE) < 10) {
            result.append("0" + cal.get(Calendar.DATE));
        } else {
            result.append(cal.get(Calendar.DATE));
        }

        if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
            result.append("-0" + cal.get(Calendar.HOUR_OF_DAY));
        } else {
            result.append("-" + cal.get(Calendar.HOUR_OF_DAY));
        }
        if (cal.get(Calendar.MINUTE) < 10) {
            result.append("0" + cal.get(Calendar.MINUTE));
        } else {
            result.append(cal.get(Calendar.MINUTE));
        }
        if (cal.get(Calendar.SECOND) < 10) {
            result.append("0" + cal.get(Calendar.SECOND));
        } else {
            result.append(cal.get(Calendar.SECOND));
        }
        result.append(cal.get(Calendar.MILLISECOND));

        return result.toString();
    }

    /*
       public static void main(String[] args){
      System.out.println(GenerateFileName.getTargetFileName("한글","user"));
      System.out.println(GenerateFileName.getTargetFileName("한글.hwp","user"));
      System.out.println(GenerateFileName.getTargetFileName("한글.신버전.exe","user"));
      System.out.println(GenerateFileName.getTargetFileName("한글.확장자.확장자.hwp","user"));
      System.out.println(GenerateFileName.getTargetFileName("한글.확장자.확장자.확장자.hwp","user"));
      System.out.println(GenerateFileName.getTargetFileName("한글.확장자.확장자.확장자.확장자.hwp","user"));
      System.out.println(GenerateFileName.getSourceFileName("한글-user-20040514-162006"));
      System.out.println(GenerateFileName.getSourceFileName("한글-user-20040514-162006.hwp"));
      System.out.println(GenerateFileName.getSourceFileName("한글.신버전-user-20040514-162006.hwp"));
      System.out.println(GenerateFileName.getSourceFileName("한글.확장자.확장자-user-20040514-162006.hwp"));
      System.out.println(GenerateFileName.getSourceFileName("한글.확장자.확장자.확장자-user-20040514-162006.hwp"));
      System.out.println(GenerateFileName.getSourceFileName("한글.확장자.확장자.확장자.확장자-user-20040514-162006.hwp"));
       }*/

}
