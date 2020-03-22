package com.bofa.codec.util.algorithm;

/**
 * @author bofa1ex
 * @since 2020/3/18
 *
 *      * crc校验原理
 *      *
 *      * 1. 将一个16位寄存器装入十六进制FFFF (全1). 将之称作CRC 寄存器. 
 *      * 2. 将报文的第一个8位字节与16位CRC寄存器的低字节异或，结果置于CRC 寄存器. 
 *      * 3. 将CRC 寄存器右移1位(向LSB 方向)，MSB 充零. 提取并检测LSB. 
 *      * 4. (如果LSB 为0): 重复步骤3 (另一次移位). 
 *      * (如果LSB 为1): 对CRC 寄存器异或多项式值 0xA001 (1010 0000 0000 0001). 
 *      * 5. 重复步骤3 和4，直到完成8 次移位。当做完此操作后，将完成对8位字节的完整操作。
 *      * 6. 对报文中的下一个字节重复步骤2 到5，继续此操作直至所有报文被处理完毕。
 *      * 7. CRC 寄存器中的最终内容为CRC 值. 
 *      * 8. 当放置CRC 值于报文时，如下面描述的那样，高低字节必须交换。
 */
public class CRCUtils {

    /******************************************************************************
     * Name:    CRC-4/ITU           x4 + x + 1
     * Poly:    0x03
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     ****************************************************************************/
    public static int crc4_itu(byte[] data, int offset, int length) {
        byte i;
        byte crc = 0;                // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) != 0)
                    crc = (byte) (((crc & 0xff) >> 1) ^ 0x0C);// 0x0C = (reverse 0x03)>>(8-4)
                else
                    crc = (byte) ((crc & 0xff) >> 1);
            }
        }
        return crc & 0xf;
    }

    /******************************************************************************
     * Name:    CRC-5/EPC           x5+x3+1
     * Poly:    0x09
     * Init:    0x09
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc5_epc(byte data[], int offset, int length) {
        byte i;
        byte crc = 0x48;        // Initial value: 0x48 = 0x09<<(8-5)
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0)
                    crc = (byte) ((crc << 1) ^ 0x48);        // 0x48 = 0x09<<(8-5)
                else
                    crc <<= 1;
            }
        }
        return (byte) (crc >> 3 & 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-5/ITU           x5+x4+x2+1
     * Poly:    0x15
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc5_itu(byte[] data, int offset, int length) {
        byte i;
        byte crc = 0;                // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc = (byte) ((crc & 0xff) >> 1);
                else
                    crc = (byte) (((crc & 0xff) >> 1) ^ 0x15);// 0x15 = (reverse 0x15)>>(8-5)
            }
        }
        return (byte) (crc & 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-5/USB           x5+x2+1
     * Poly:    0x05
     * Init:    0x1F
     * Refin:   True
     * Refout:  True
     * Xorout:  0x1F
     * Note:
     *****************************************************************************/
    public static byte crc5_usb(byte[] data, int offset, int length) {
        byte i;
        byte crc = 0x1F;                // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc = (byte) ((crc & 0xff) >> 1);
                else
                    crc = (byte) (((crc & 0xff) >> 1) ^ 0x14);// 0x14 = (reverse 0x05)>>(8-5)
            }
        }
        return (byte) (crc ^ 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-6/ITU           x6+x+1
     * Poly:    0x03
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc6_itu(byte[] data, int offset, int length) {
        byte i;
        byte crc = 0;         // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc = (byte) ((crc & 0xff) >> 1);
                else
                    crc = (byte) (((crc & 0xff) >> 1) ^ 0x30);// 0x30 = (reverse 0x03)>>(8-6)
            }
        }
        return (byte) (crc & 0x3f);
    }

    /******************************************************************************
     * Name:    CRC-7/MMC           x7+x3+1
     * Poly:    0x09
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Use:     MultiMediaCard,SD,ect.
     *****************************************************************************/
    public static byte crc7_mmc(byte[] data, int offset, int length) {
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];        // crc ^= *data; data++;
            for (i = 0; i < 8; i++) {
                if ((crc & 0x80) == 0)
                    crc <<= 1;
                else
                    crc = (byte) ((crc << 1) ^ 0x12);        // 0x12 = 0x09<<(8-7)
            }
        }
        return (byte) (crc >> 1 & 0x7f);
    }

    /******************************************************************************
     * Name:    CRC-8               x8+x2+x+1
     * Poly:    0x07
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static int crc8(byte[] data, int offset, int length) {
        byte i;
        int crc = 0x00;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 0x80) == 0)
                    crc <<= 1;
                else
                    crc = (crc << 1) ^ 0x07;
            }
        }
        return crc & 0xff;
    }

    /******************************************************************************
     * Name:    CRC-8/ITU           x8+x2+x+1
     * Poly:    0x07
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x55
     * Alias:   CRC-8/ATM
     *****************************************************************************/
    public static int crc8_itu(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 0x80) == 0)
                    crc <<= 1;
                else
                    crc =  ((crc << 1) ^ 0x07);
            }
        }
        return crc ^ 0x55 & 0xff;
    }

    /******************************************************************************
     * Name:    CRC-8/ROHC          x8+x2+x+1
     * Poly:    0x07
     * Init:    0xFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static int crc8_rohc(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 0x80) == 0)
                    crc =  ((crc & 0xff) >> 1);
                else
                    crc =  (((crc & 0xff) >> 1) ^ 0xE0);
            }
        }
        return crc & 0xff;
    }

    /******************************************************************************
     * Name:    CRC-8/MAXIM         x8+x5+x4+1
     * Poly:    0x31
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Alias:   DOW-CRC,CRC-8/IBUTTON
     * Use:     Maxim(Dallas)'s some devices,e.g. DS18B20
     *****************************************************************************/
    public static int crc8_maxim(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; i++) {
                if ((crc & 1) == 0)
                    crc =  ((crc & 0xff) >> 1);
                else
                    crc =  (((crc & 0xff) >> 1) ^ 0x8C);
            }
        }
        return crc & 0xff;
    }

    /******************************************************************************
     * Name:    CRC-16/IBM          x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Alias:   CRC-16,CRC-16/ARC,CRC-16/LHA
     *****************************************************************************/
    public static int crc16_ibm(byte data[], int offset, int length) {
        byte i;
        int crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc =  (crc >> 1);
                else
                    crc =  ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/MAXIM        x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Note:
     *****************************************************************************/
    public static int crc16_maxim(byte[] data, int offset, int length) {
        byte i;
        int crc = 0x0000;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 0x0001) == 0)
                    crc =  (crc >> 1);
                else
                    crc =  ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return  crc ^ 0xffff;    // crc^0xffff
    }

    /******************************************************************************
     * Name:    CRC-16/USB          x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Note:
     *****************************************************************************/
    public static int crc16_usb(byte[] data, int offset, int length) {
        byte i;
        int crc =  0xffff;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];            // crc ^= *data; data++;
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc =  (crc >> 1);
                else
                    crc =  ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return  ~crc;    // crc^0xffff
    }

    /******************************************************************************
     * Name:    CRC-16/MODBUS       x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Note:
     *****************************************************************************/
    public static int crc16_modbus(byte[] data, int offset, int length) {
        byte i;
        int crc =  0xffff;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];            // crc ^= *data; data++;
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) == 0)
                    crc =  (crc >> 1);
                else
                    crc =  ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return  crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/CCITT        x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Alias:   CRC-CCITT,CRC-16/CCITT-TRUE,CRC-16/KERMIT
     *****************************************************************************/
    public static int crc16_ccitt(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) != 0)
                    crc =  ((crc >> 1) ^ 0x8408);        // 0x8408 = reverse 0x1021
                else
                    crc =  (crc >> 1);
            }
        }
        return crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/CCITT-FALSE   x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0xFFFF
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000
     * Note:
     *****************************************************************************/
    public static int crc16_ccitt_false(byte[] data, int offset, int length) {
        byte i;
        int crc =  0xffff;        //Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^=  (data[j]) << 8; // crc ^= (uint6_t)(*data)<<8; data++;
            for (i = 0; i < 8; ++i) {
                if ((crc & 0x8000) != 0)
                    crc =  ((crc << 1) ^ 0x1021);
                else
                    crc <<= 1;
            }
        }
        return crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/X25          x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0XFFFF
     * Note:
     *****************************************************************************/
    public static int crc16_x25(byte[] data, int offset, int length) {
        byte i;
        int crc =  0xffff;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) != 0)
                    crc =  ((crc >> 1) ^ 0x8408);        // 0x8408 = reverse 0x1021
                else
                    crc =  (crc >> 1);
            }
        }
        return  ~crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/XMODEM       x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0x0000
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000
     * Alias:   CRC-16/ZMODEM,CRC-16/ACORN
     *****************************************************************************/
    public static int crc16_xmodem(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;            // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^=  data[j] << 8;
            for (i = 0; i < 8; ++i) {
                if ((crc & 0x8000) != 0)
                    crc =  ((crc << 1) ^ 0x1021);
                else
                    crc <<= 1;
            }
        }
        return crc & 0xffff;
    }

    /******************************************************************************
     * Name:    CRC-16/DNP          x16+x13+x12+x11+x10+x8+x6+x5+x2+1
     * Poly:    0x3D65
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Use:     M-Bus,ect.
     *****************************************************************************/
    public static int crc16_dnp(byte[] data, int offset, int length) {
        byte i;
        int crc = 0;            // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) != 0)
                    crc =  ((crc >> 1) ^ 0xA6BC);        // 0xA6BC = reverse 0x3D65
                else
                    crc =  (crc >> 1);
            }
        }
        return  ~crc & 0xffff;            // crc^Xorout
    }

    /******************************************************************************
     * Name:    CRC-32  x32+x26+x23+x22+x16+x12+x11+x10+x8+x7+x5+x4+x2+x+1
     * Poly:    0x4C11DB7
     * Init:    0xFFFFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFFFFF
     * Alias:   CRC_32/ADCCP
     * Use:     WinRAR,ect.
     *****************************************************************************/
    public static int crc32(byte[] data, int offset, int length) {
        byte i;
        int crc = 0xffffffff;        // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i) {
                if ((crc & 1) != 0)
                    crc = (crc >> 1) ^ 0xEDB88320;// 0xEDB88320= reverse 0x04C11DB7
                else
                    crc = (crc >> 1);
            }
        }
        return ~crc;
    }


    /******************************************************************************
     * Name:    CRC-32/MPEG-2  x32+x26+x23+x22+x16+x12+x11+x10+x8+x7+x5+x4+x2+x+1
     * Poly:    0x4C11DB7
     * Init:    0xFFFFFFF
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000000
     * Note:
     *****************************************************************************/
    public static int crc32_mpeg_2(byte[] data, int offset, int length) {
        byte i;
        int crc = 0xffffffff;  // Initial value
        length += offset;
        for (int j = offset; j < length; j++) {
            crc ^= data[j] << 24;
            for (i = 0; i < 8; ++i) {
                if ((crc & 0x80000000) != 0)
                    crc = (crc << 1) ^ 0x04C11DB7;
                else
                    crc <<= 1;
            }
        }
        return crc;
    }
}
