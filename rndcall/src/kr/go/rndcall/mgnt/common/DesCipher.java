/* ---------------------------------------------------------------------
    프 로 그 램 명 : DesCipher.java
    작    성    자 : 곽중선
    작    성    일 : 2001/10/26
    설         명  : DES(Data Encryption Standard) 암호화 알고리즘을 구현한 클래스
    수    정    자 :
    수  정  일  자 :
    수  정  사  유 :
    참  조  파  일 :
    ver            : 1.0
--------------------------------------------------------------------------- */

package kr.go.rndcall.mgnt.common;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class DesCipher
{
    private byte [] initVec;    // 초기화 벡터(IV)
    private byte [] key;        // 암호화 키( 56 bit만 사용, 최상위 8bit은 사용안함 )

    // 초기순열, 역초기순열, 확장순열, 순열함수
    private int[] IP = {
        58,50,42,34,26,18,10, 2,60,52,44,36,28,20,12, 4,
        62,54,46,38,30,22,14, 6,64,56,48,40,32,24,16, 8,
        57,49,41,33,25,17, 9, 1,59,51,43,35,27,19,11, 3,
        61,53,45,37,29,21,13, 5,63,55,47,39,31,23,15, 7
    };

    private int[] IIP = {
        40, 8,48,16,56,24,64,32,39, 7,47,15,55,23,63,31,
        38, 6,46,14,54,22,62,30,37, 5,45,13,53,21,61,29,
        36, 4,44,12,52,20,60,28,35, 3,43,11,51,19,59,27,
        34, 2,42,10,50,18,58,26,33, 1,41, 9,49,17,57,25
    };

    private int[] E = {
        32, 1, 2, 3, 4, 5,
         4, 5, 6, 7, 8, 9,
         8, 9,10,11,12,13,
        12,13,14,15,16,17,
        16,17,18,19,20,21,
        20,21,22,23,24,25,
        24,25,26,27,28,29,
        28,29,30,31,32, 1
    };

    private int[] P = {
        16, 7,20,21,
        29,12,28,17,
         1,15,23,26,
         5,18,31,10,
         2, 8,24,14,
        32,27, 3, 9,
        19,13,30, 6,
        22,11, 4,25
    };

    // S-BOX
    private int S[][] = {
        {
        14, 4,13, 1, 2,15,11, 8, 3,10, 6,12, 5, 9, 0, 7,
         0,15, 7, 4,14, 2,13, 1,10, 6,12,11, 9, 5, 3, 8,
         4, 1,14, 8,13, 6, 2,11,15,12, 9, 7, 3,10, 5, 0,
        15,12, 8, 2, 4, 9, 1, 7, 5,11, 3,14,10, 0, 6,13
        },
        {
        15, 1, 8,14, 6,11, 3, 4, 9, 7, 2,13,12, 0, 5,10,
         3,13, 4, 7,15, 2, 8,14,12, 0, 1,10, 6, 9,11, 5,
         0,14, 7,11,10, 4,13, 1, 5, 8,12, 6, 9, 3, 2,15,
        13, 8,10, 1, 3,15, 4, 2,11, 6, 7,12, 0, 5,14, 9
        },
        {
         10, 0, 9,14, 6, 3,15, 5, 1,13,12, 7,11, 4, 2, 8,
         13, 7, 0, 9, 3, 4, 6,10, 2, 8, 5,14,12,11,15, 1,
         13, 6, 4, 9, 8, 15,3, 0,11, 1, 2,12, 5,10,14, 7,
          1,10,13, 0, 6, 9, 8, 7, 4,15,14, 3,11, 5, 2,12
        },
        {
          7,13,14, 3, 0, 6, 9,10, 1, 2, 8, 5,11,12, 4,15,
         13, 8,11, 5, 6,15, 0, 3, 4, 7, 2,12, 1,10,14, 9,
         10, 6, 9, 0,12,11, 7,13,15, 1, 3,14, 5, 2, 8, 4,
          3,15, 0, 6,10, 1,13, 8, 9, 4, 5,11,12, 7, 2,14
        },
        {
          2,12, 4, 1, 7,10,11, 6, 8, 5, 3,15,13, 0,14, 9,
         14,11, 2,12, 4, 7,13, 1, 5, 0,15,10, 3, 9, 8, 6,
          4, 2, 1,11,10,13, 7, 8,15, 9,12, 5, 6, 3, 0,14,
         11, 8,12, 7, 1,14, 2,13, 6,15, 0, 9,10, 4, 5, 3
        },
        {
         12, 1,10,15, 9, 2, 6, 8, 0,13, 3, 4,14, 7, 5,11,
         10,15, 4, 2, 7,12, 9, 5, 6, 1,13,14, 0,11, 3, 8,
          9,14,15, 5, 2, 8,12, 3, 7, 0, 4,10, 1,13,11, 6,
          4, 3, 2,12, 9, 5,15,10,11,14, 1, 7, 6, 0, 8,13
        },
        {
          4,11, 2,14,15, 0, 8,13, 3,12, 9, 7, 5,10, 6, 1,
         13, 0,11, 7, 4, 9, 1,10,14, 3, 5,12, 2,15, 8, 6,
          1, 4,11,13,12, 3, 7,14,10,15, 6, 8, 0, 5, 9, 2,
          6,11,13, 8, 1, 4,10, 7, 9, 5, 0,15,14, 2, 3,12
        },
        {
         13, 2, 8, 4, 6,15,11, 1,10, 9, 3,14, 5, 0,12, 7,
          1,15,13, 8,10, 3, 7, 4,12, 5, 6,11, 0,14, 9, 2,
          7,11, 4, 1, 9,12,14, 2, 0, 6,10,13,15, 3, 5, 8,
          2, 1,14, 7, 4,10, 8,13,15,12, 9, 0, 3, 5, 6,11
        }
    };

    // 순열선택1, 순열선택2, 좌측 이동표
    private int[] PC1 =
    {
         57,49,41,33,25,17, 9, 1,58,50,42,34,26,18,
         10, 2,59,51,43,35,27,19,11, 3,60,52,44,36,
         63,55,47,39,31,23,15, 7,62,54,46,38,30,22,
         14, 6,61,53,45,37,29,21,13, 5,28,20,12, 4
    };

    private int[] PC2 =
    {
         14, 4,11,24, 1, 5, 3,28,15, 6,21,10,
         23,19,12, 4,26, 8,16, 7,27,20,13, 2,
         41,52,31,37,47,55,30,40,51,45,33,48,
         44,49,39,56,34,53,46,42,50,36,29,32
    };

    private int keySchedule[]={ 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    // 순열 종류
    private int MODE_IP = 0, MODE_IIP=1, MODE_E=2, MODE_P=3, MODE_PC1=4, MODE_PC2=5;

    private int BIT[] = { 128,64,32,16,8,4,2,1 };
    private int JBIT = 8;


    public DesCipher()
    {
        initialize( "", "" );
    }


    /****************************************************/
    /*                                                  */
    /*    함수명 : Decode()                             */
    /*    기  능 : DES 복호화를 수행한다.               */
    /*                                                  */
    /*    입력값 : String strSrc( 암호문 )              */
    /*    리턴값 : String 평문                          */
    /*                                                  */
    /****************************************************/
    synchronized public String Decode(String strSrc)
    {
        // 암호 decode 시, user_sign_password 를 사용하지 않는 사이트는 ''값을 사용하므로 null에 대한 처리 필요
        if(strSrc.length() == 0 || "".equals(strSrc)) return "0";
        //if( StringFormat.nullToDefault(strSrc, "").length() == 0 ) return "0";

        StringBuffer strDec = new StringBuffer();

        // 인코딩된 문자를 원래의 문자열로 변형한다.
        String strConvertedSrc = NumberingStringToString( strSrc );

        int nLen = strConvertedSrc.length();                // 암호문의 길이

        byte [] imsiVec = initVec;                 // 임시 벡터
        byte [] imsiResult = new byte[64];
        byte [] j = new byte[8];
        byte [] c = new byte[8];                   // j bit

        for( int i=0; i<nLen; i++ )
        {
            // 64-JBIT | j
            if( i > 0 ) imsiVec = shiftRegister( imsiVec, JBIT, j );

            // DES 수행
            imsiResult = Des( imsiVec, key );

            // 결과에서 MSB JBIT 만큼 추출
            for( int n=0; n<8; n++ )
                j[n] = imsiResult[n];

            c = toBinary( strConvertedSrc.substring(i,i+1), 1 );

            for( int n=0; n<8; n++ )
                c[n] = (byte) (c[n] ^ j[n]);

            strDec.append( binToString(c,1));      // 결과 저장
        }
        
        String strDec_UTF = "";
        try{
    		strDec_UTF = URLDecoder.decode(strDec.toString(), "UTF-8");
    	} catch(Exception e) {
    		System.out.println("URLDecoder :: " + e);
    	}
        
        return strDec_UTF;
    }


    /****************************************************/
    /*                                                  */
    /*  함수명 : Encode()                               */
    /*  기  능 : DES 암호화를 수행한다.                 */
    /*                                                  */
    /*  입력값 : CString strSrc( 평문 )                 */
    /*  리턴값 : CString 암호문                         */
    /*                                                  */
    /****************************************************/
    synchronized public String Encode( String strSrc )
    {
        
    	try{
    		strSrc = URLEncoder.encode(strSrc, "UTF-8");
    	} catch(Exception e) {
    		System.out.println("URLEncoder :: " + e);
    	}
    	
    	// OFB 모드 사용
        StringBuffer strEnc = new StringBuffer(1024);

        int nLen = strSrc.length();            // 평문의 길이
        byte [] imsiVec = initVec;             // 임시 벡터
        byte [] imsiResult = new byte[64];
        byte [] j = new byte[8];               // j bit
        byte [] c = new byte[8];

        for( int i=0; i<nLen; i++ )
        {
            // 64-JBIT | j
            if( i > 0 ) imsiVec = shiftRegister( imsiVec, JBIT, j );

            // DES 수행
            imsiResult = Des( imsiVec, key );
            for( int n=0; n<8; n++ )
            {
                j[n] = imsiResult[n];
            }

            c = toBinary( strSrc.substring(i,i+1), 1 );
            for( int n=0; n<8; n++ )
                c[n] = (byte) (c[n] ^ j[n]);

            // 결과 저장
            strEnc.append( binToNumberingString(c,1));
        }

        return strEnc.toString();
    }


    private byte[] shiftRegister( byte x[], int j, byte y[] )
    {
        byte[] tmp = new byte[64];

        for( int i=0; i<64; i++ )
        {
            if( i < j )
                tmp[i] = y[i];
            else
                tmp[i] = x[i-j];
        }
        return tmp;
    }


    /****************************************************/
    /*                                                  */
    /*  함수명 : Des()                                  */
    /*  기  능 : 평문과 키를 받아 암호화를 수행한다.    */
    /*                                                  */
    /*  입력값 : BIT64 oriData( 64bit 데이터 )          */
    /*           BIT64 oriKey( 56bit 키 )               */
    /*  리턴값 : BIT64(Des 결과값)                      */
    /*                                                  */
    /****************************************************/
    public byte [] Des( byte [] oriData, byte [] oriKey )
    {
        // 64bit 데이터
        byte [] data = oriData;
        // 56bit key
        byte [] key = oriKey;

        byte [] subKey = new  byte[48];
        byte [] imsiKey = new byte[56];

        data    = Permutation( data, MODE_IP ); // 데이터 초기순열
        imsiKey = Permutation( key, MODE_PC1 ); // 키 순열선택1

        byte [] R = new byte[32];
        byte [] L = new byte[32];
        byte [] expansion = new byte[48];
        byte [] imsiData32 = new byte [32];
        byte [] C = new byte[28];
        byte [] D = new byte[28];

        for( int i=0; i<32; i++ )
        {
            L[i] = data[i];
            R[i] = data[i+32];
        }

        for ( int i=0; i<28; i++ )
        {
            C[i] = imsiKey[i];                  // 28bit key C(left)
            D[i] = imsiKey[i+28];               // 28bit key D(right)
        }

        // 16번 반복 처리
        for( int i=0; i<16; i++ )
        {
            C = leftShift(C,i);                 // 키 좌측이동
            D = leftShift(D,i);                 // 키 좌측이동

            // 임시 키 통합
            for (int k=0; k<28; k++)
            {
                imsiKey[k] = C[i];
                imsiKey[k+28] = D[i];
            }
            subKey = Permutation( imsiKey, MODE_PC2 ); // 키 순열선택2
            expansion = Permutation( R, MODE_E );      // 확장 순열

            // 확장순열 XOR 키
            for( int k=0; k<48;k++ )
                expansion[i] = (byte)(expansion[i] ^ subKey[i]);

            imsiData32 = SBox( expansion );                // S-Box

            imsiData32 = Permutation( imsiData32, MODE_P); // 순열

            for( int k=0;k<32; k++ )
                imsiData32[i] = (byte)(R[i] ^ imsiData32[i]);

            L = R;                                         // L <- R
            R = imsiData32;                                // R <- XOR 결과
        }

        for (int i=0; i<32; i++)
        {
            data[i] = R[i];
            data[i+32] = L[i];
        }

        data = Permutation( data, MODE_IIP);               // 역초기 순열

        return data;
    }


    private byte[] leftShift( byte x[], int j )
    {
        byte[] temp = new byte [28];
        int n = keySchedule[j];
        int i = 0;

        for( i=0; i<28-n; i++ )
            temp[i] = x[i+n];

        if( n == 1 )
            temp[i] = x[0];
        else
        {
            temp[i++] = x[0];
            temp[i]   = x[1];
        }
        return temp;
    }


    /****************************************************/
    /*                                                  */
    /*  함수명 : Permutation()                          */
    /*  기  능 : 순열표에 의한 순열을 수행한다.         */
    /*                                                  */
    /*  입력값 : void *pData(순열을 위한 입력값)        */
    /*           int nMode( 순열 선택 )                 */
    /*  리턴값 : void * 순열 수행 후 결과값             */
    /*                                                  */
    /****************************************************/
    private byte[] Permutation( byte [] data, int nMode )
    {
        byte[] temp = new byte[64];
        switch(nMode)
        {
            case 0:                         // MODE_IP = 0
                for( int i=0; i<64; i++ )
                    temp[i] = data[IP[i]-1];
                return temp;

            case 1:                         // MODE_IIP = 1
                for( int i=0; i<64; i++ )
                    temp[i] = data[IIP[i]-1];
                return temp;

            case 2:                         // MODE_E = 2
                for (int i=0; i<48; i++)
                    temp[i] = data[E[i]-1];
                return temp;

            case 3:                         // MODE_P = 3
                for (int i=0; i<32; i++)
                    temp[i] = data[P[i]-1];
                return temp;

            case 4:                         // MODE_PC1 = 4
                for (int i=0; i<56; i++)
                    temp[i] = data[PC1[i]-1];
                return temp;

            case 5:                         // MODE_PC2 = 5
                for (int i=0; i<48; i++)
                    temp[i] = data[PC2[i]-1];
                return temp;
        }

        return null;        // dummy
    }


    /****************************************************/
    /*                                                  */
    /*  함수명 : SBox()                                 */
    /*  기  능 : 48bit->32bit 계산을 수행하는 SBOX      */
    /*                                                  */
    /*  입력값 : BIT64 data( 48bit 데이터 )             */
    /*  리턴값 : BIT32( 32bit 결과값)                   */
    /*                                                  */
    /****************************************************/
    private byte[] SBox( byte[] data )
    {
        byte [] result = new byte[32];          // 32bit 결과 저장 변수
        byte [] imsiData = data;                // 48bit 데이터를 임시 저장

        int row, col,snum,f;                    // sbox의 행과 열

        for( int i=0; i < 8; i++ )
        {
            row = col = 0;

            // 처음 행의 하위 1bit 획득
            row = 2*imsiData[i*6] + imsiData[i*6+5];

            // 열값을 갖는 4bit 획득
            col = 8*imsiData[i*6+1] + 4*imsiData[i*6+2] + 2*imsiData[i*6+3] + imsiData[i*6+4];

            // 행값의 상위 1bit 획득
            // sbox 테이블에 의한 4bit 값 획득 및 저장
            snum = S[i][row*16 + col];
            if( (f=snum & 8) == 8 )
                result[i*4] = 1;
            else
                result[i*4]=0;

            if( (f=snum & 4) == 4 )
                result[i*4+1] = 1;
            else
                result[i*4+1]=0;

            if( (f=snum & 2) == 2 )
                result[i*4+2] = 1;
            else
                result[i*4+2]=0;

            if( (f=snum & 1) == 1 )
                result[i*4+3] = 1;
            else
                result[i*4+3]=0;
        }

        return result;
    }


    public void initialize( String strVec, String strKey )
    {
        if( strVec == "" )
            strVec = "ABCDABCD";

        if( strKey == "" )
            strKey = "SUPERKEY";

        initVec = toBinary( strVec, 8 );
        key = toBinary( strKey, 8 );
    }


    private byte[] toBinary( String x, int n )
    {
        byte[] binary = new byte[64];

        int check;
        for( int i=0; i<n; i++ )
        {
            check = (int)x.charAt(i);
            for( int j=0; j<8; j++ )
            {
                if( check >= BIT[j] )
                {
                    binary[(i)*8+j] = 1;
                    check = check-BIT[j];
                }
                else
                    binary[(i)*8+j] = 0;
            }
        }

        return binary;
    }


    private String binToString( byte [] b, int n )
    {
        StringBuffer temp = new StringBuffer(256);
        char ch;

        for( int i=0; i<n; i++ )
        {
            ch = (char)(b[(i*8)+1]*64 + b[(i*8)+2]*32 +
                        b[(i*8)+3]*16 + b[(i*8)+4]*8 + b[(i*8)+5]*4 +
                        b[(i*8)+6]*2 + b[(i*8)+7]*1);
            temp.append(ch);
        }

        return temp.toString();
    }


    /****************************************************/
    /*                                                  */
    /*  함수명 : binToNumberingString()                 */
    /*  기  능 : binToString에서 char로 변환하지 않고,  */
    /*           해당 값을 integer형태로 반환해서       */
    /*           숫자를 그대로 가지고 있게 한다.        */
    /*           char타입이면, 특수 코드인코딩처리에서  */
    /*           문제가 발생하기 때문.                  */
    /*  입력값 : byte array , integer                   */
    /*  리턴값 : String  숫자형으로 변환된 문자열       */
    /*                                                  */
    /****************************************************/
    private String binToNumberingString( byte [] b , int n )
    {
        StringBuffer strTemp = new StringBuffer(64);
        int nNumber;
        char cChar;

        for( int i=0; i<n; i++ )
        {
            nNumber = (b[(i*8)+1]*64 + b[(i*8)+2]*32 +
                       b[(i*8)+3]*16 + b[(i*8)+4]*8 + b[(i*8)+5]*4  +
                       b[(i*8)+6]*2 + b[(i*8)+7]*1 );

            /* 숫자를 3자리의 문자로 변형하여 더하도록 한다. */
            /* 즉, 모든 문자는 3자리가 되도록 한다.          */
            if ( ( nNumber > 99 ) && ( nNumber < 200 ) )
            {
                cChar = '1';
                nNumber = nNumber - 100;
                strTemp.append(cChar);
            }
            else if ( nNumber > 199 )
            {
                cChar = '2';
                strTemp.append(cChar);
            }
            else
            {
                cChar = '0';
                strTemp.append(cChar);
            }

            if ( nNumber < 10 )
              strTemp.append("0");

            strTemp.append(nNumber);
        }
        return strTemp.toString();
    }


    /********************************************************/
    /*                                                      */
    /*  함수명 : NumberingStringToString()                  */
    /*  기  능 : 인코딩되어 있는 숫자를                     */
    /*          디코딩하기 위해서 원래의                    */
    /*          char문자열로 변형한다.                      */
    /*  입력값 : strNumberingString                         */
    /*  리턴값 : String  문자열로 변환된 Decoding된 문자열  */
    /*                                                      */
    /********************************************************/
    private String NumberingStringToString(String strNumberingString)
    {
        StringBuffer strConvertedBuffer  = new StringBuffer(64);
        StringBuffer strConvertingBuffer = new StringBuffer(100);

        strConvertingBuffer.append(strNumberingString);

        char cChar;
        int nNumber = 0;

        while( nNumber + 1 < strConvertingBuffer.length() )
        {
            cChar = (char)( Integer.parseInt( strConvertingBuffer.substring(nNumber,nNumber+3) ) );
            strConvertedBuffer.append( cChar );
            nNumber = nNumber + 3;
        }

        return strConvertedBuffer.toString();
    }


/*
	public static void main(String[] args){

		DesCipher dc = new DesCipher();
		System.out.println("인코딩 결과"+(String)dc.Encode("TIER"));
		//System.out.println(dc.Decode("087117015009126067037053"));		
	}
*/
}
