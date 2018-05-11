/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Main {

    static String template =
            "                    <sdrw:csv-field type=\"node\">\n" +
            "                      <nodeType>preferences-group</nodeType>\n" +
            "                      <sdrw:field-name type=\"preference\" needRevise=\"false\" changed=\"true\">CDR_{0}</sdrw:field-name>\n" +
            "                      <sdrw:field-type type=\"preference\" needRevise=\"false\">STRING</sdrw:field-type>\n" +
            "                      <sdrw:field-header type=\"preference\" needRevise=\"false\" changed=\"true\">{0}</sdrw:field-header>\n" +
            "                    </sdrw:csv-field>\n";


    static String[] ids = {
            "CR",
            "OA",
            "OTI",
            "CgPA",
            "CgPA_NOA",
            "CdPN",
            "CdPN_NOA",
            "CgPA_Norm",
            "CdPN_Norm",
            "FTN",
            "FTN_Norm",
            "SU",
            "TN",
            "BER",
            "SK",
            "TCS",
            "SL",
            "CS",
            "CPC",
            "CC",
            "LGID",
            "CPPI",
            "NOAT",
            "FATS",
            "CCTS",
            "HGF",
            "AIDL",
            "DRC",
            "CAU",
            "RSF",
            "BUSY",
            "CALL_START_TIME",
            "MSC",
            "VLR",
            "OCGI",
            "TCGI",
            "SCP_ID",

            "DIA_SUBSC_ID",
            "DIA_SESSION_ID",
            "DIA_SVCID",
            "DIA_CALLING_ADDR",
            "DIA_CALLED_ADDR",
            "DIA_ORIG_DIGIT",
            "DIA_TMSC",
            "DIA_ORIG_HOST",
            "DIA_ORIG_REALM",
            "DIA_DESTINATION_HOST",
            "DIA_DESTINATION_REALM",
            "DIA_CALLID",
            "DIA_START_TIME",
            "DIA_GSU",
            "DIA_CALLED_TYPE",
            "DIA_RCODE",
            "DIA_USED_TIME",
            "DIA_SCHG",
            "DIA_DISCOUNT",
            "DIA_IMEI",
            "DIA_TCGI",
            "DIA_RBAL",

            "PCN_ERROR_CODE",
            "PCN_VALUES",
            "PCN_CHANEL",
            "PCN_RECIPIENT",
            "PCN_RESPONDER_ADDRESS",
            "PCN_SENDER_ADDRESS",
            "PCN_RESPONSE_TIME",
            "PCN_SENT",
            "PCN_STATUS",
            "PCN_STRING",
            "USSD_Payload_length",

            "HTTP_Request_Time",
            "HTTP_Response_Time",
            "HTTP_Response_Code",
            "HTTP_Source_address",
            "HTTP_Destination_address",
            "HTTP_ASP_ID",
            "HTTP_FROM",
            "HTTP_TO",

            "DIA_ORIG_HOST",
            "DIA_SESSION_ID",
            "DIA_ORIG_REALM",
            "DIA_DESTINATION_HOST",
            "DIA_DESTINATION_REALM",
            "DIA_SERVICE_CONTEXT_ID",
            "DIA_SUBSCRIPTION",
            "DIA_TT",
            "DIA_SK",
            "DIA_RESULT_CODE",
            "DIA_NUMBEROFBALANCE",
            "DIA_FACEVALUE",
            "DIA_OFFERCODE",
            "DIA_BALANCEID",
            "DIA_BALANCEVALUE",
            "DIA_BALANCETYPE",
            "DIA_ACTIVEDATE",
            "DIA_GRACEDATE",
            "DIA_ATEMPT_LEFT",
            "DIA_VOUCHERNUMBER",
            "DIA_VOUCERPIN",

            "HTTP_REQUEST_TYPE",
            "HTTP_CC_PARTY_ID",
            "HTTP_Response_Code",
            "HTTP_Source_address",
            "HTTP_Destination_address",
            "HTTP_TMESTAMP",
            "HTTP_RESPONSE_TIMESTAMP",
            "HTTP_ASP_ID",

    };



    static String[] ids_old = {
            "OA",
            "OTI",
            "SN",
            "CgPA",
            "CgPA_NOA",
            "CdPN",
            "CdPN_NOA",
            "CgPA_Norm",
            "CdPN_Norm",
            "FTN",
            "FTN_Norm",
            "SU",
            "TN",
            "BER",
            "SK",
            "TCS",
            "CS",
            "CPC",
            "CC",
            "LGID",
            "CPPI",
            "NOAT",
            "CBAT",
            "FATS",
            "CCTS",
            "HGF",
            "AIDL",
            "CAU",
            "RSF",
            "BUSY",
            "CALL START TIME",
            "SCP_ID",


            "DIA_SUBSC_ID",
            "DIA_SESSION_ID",
            "DIA_SVCID",
            "DIA_CALLING_ADDR",
            "DIA_CALLED_ADDR",
            "DIA_SK",
            "DIA_ORIG_DIGIT",
            "DIA_VLRID",
            "DIA_ORIG_HOST",
            "DIA_ORIG_REALM",
            "DIA_DESTINATION_HOST",
            "DIA_DESTINATION_REALM",
            "DIA_CALLID",
            "DIA_START_TIME",
            "DIA_GSU",
            "DIA_CALLED_TYPE",
            "DIA_SID",
            "DIA_RCODE",
            "DIA_USED_TIME",
            "DIA_SCHG",
            "DIA_BFT",
            "DIA_DISCOUNT",
            "DIA_IMEI",
            "DIA_OCGI",
            "DIA_TCGI",
            "DIA_PPID",
            "DIA_RBAL",
            "MSC",
            "VLR",

            "PCN_ERROR_CODE",
            "PCN_VALUES",
            "PCN_CHANEL",
            "PCN_RECIPIENT",
            "PCN_RESPONDER_ADDRESS",
            "PCN_SENDER_ADDRESS",
            "PCN_RESPONSE_TIME",
            "PCN_SENT",
            "PCN_STATUS",
            "PCN_STRING",
            "USSD_Payload_length",

            "HTTP_Request_Time",
            "HTTP_Response_Time",
            "HTTP_Response_Code",
            "HTTP_Source_address",
            "HTTP_Destination_address",
            "HTTP_ASP_ID",
            "HTTP_FROM",
            "HTTP_TO",

            "DIA_ORIG_HOST",
            "DIA_SESSION_ID",
            "DIA_ORIG_REALM",
            "DIA_DESTINATION_HOST",
            "DIA_DESTINATION_REALM",
            "DIA_SERVICE_CONTEXT_ID",
            "DIA_SUBSCRIPTION",
            "DIA_TT",
            "DIA_SK",
            "DIA_RESULT_CODE",
            "DIA_NUMBEROFBALANCE",
            "DIA_FACEVALUE",
            "DIA_OFFERCODE",
            "DIA_BALANCEID",
            "DIA_BALANCEVALUE",
            "DIA_BALANCETYPE",
            "DIA_ACTIVEDATE",
            "DIA_GRACEDATE",
            "DIA_ATEMPT_LEFT",
            "DIA_VOUCHERNUMBER",
            "DIA_VOUCERPIN",

            "HTTP_REQUEST_TYPE",
            "HTTP_CC_PARTY_ID",
            "HTTP_Response_Code",
            "HTTP_Source_address",
            "HTTP_Destination_address",
            "HTTP_TMESTAMP",
            "HTTP_RESPONSE_TIMESTAMP",
            "HTTP_ASP_ID",
    };

    public static void main(String[] args) throws ParseException {
//        byte[] bytes = new byte[] {127, 30, 10};
//        System.out.println(bytesToHexString(bytes));
//        System.out.println("0x"+decode(bytes, 0, bytes.length));
//        System.out.printf("0x%x%x%x%n", bytes[0],bytes[1],bytes[2]);
//        System.out.printf("0x%02x%n", 15);
        //        final long startTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").parse("2017_12_30_20_30_35").getTime();
//        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
////        c.setTime(new Date());
//        c.setTimeInMillis(startTime);
//        System.out.println("YEAR " + c.get(Calendar.YEAR));
//        System.out.println("MONTH " + (c.get(Calendar.MONTH) + 1));
//        System.out.println("DAY_OF_MONTH " + c.get(Calendar.DAY_OF_MONTH));
//        System.out.println("HOUR_OF_DAY " + c.get(Calendar.HOUR_OF_DAY));
//        System.out.println("MINUTE " + c.get(Calendar.MINUTE));
//        System.out.println("SECOND " + c.get(Calendar.SECOND));
//        System.out.println("WEEKDAY " + c.get(Calendar.DAY_OF_WEEK));

        StringBuilder msg = new StringBuilder();
//
//
        for(String id : ids) {
            msg.append(MessageFormat.format(template, id));
        }

        System.out.println(msg.toString());
    }

    private static String bytesToHexString(byte[] origTransactionId) {
        StringBuilder origTransactionIdStr = new StringBuilder("0x");
        int res = 0;
        for(byte num: origTransactionId) {
            if (num < 0) {
                res = num + 256;
            } else {
                res = num;
            }
            String byteVal = Integer.toString(res, 16);
            origTransactionIdStr.append((byteVal.length() == 2) ? byteVal : ("0" + byteVal));
        }
        return origTransactionIdStr.toString();
    }

    private static final char toString[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String decode(byte[] data, int pos, int endPos) {
        StringBuilder digits = new StringBuilder((endPos - pos) * 2);
        for (; pos < endPos; pos++) {
            byte value = data[pos];
            digits.append(toString[(value & 0x0F)]);
            if ((value = (byte) ((value >>> 4) & 0x0F)) != 0x0F)
                digits.append(toString[value]);
        }
        return digits.toString();
    }


}
