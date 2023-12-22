<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Re-Kyc</title>
</head>
<style>
    body {
        font-family: Arial, Helvetica, sans-serif;
        border: 4px solid rgb(10, 99, 253);
    }

    table {
        border-collapse: collapse;
        width: 100%;
        font-size: 12px;
        padding: none;
        border: none;
        border-spacing: 0;
    }

    td {
        padding: 4px;
    }

    input {
        margin: none;
        padding: none;
        margin-bottom: 2px;
        margin-right: 2px;
    }
    #date{
        display: block;
        margin-right: 90px;
    }
    #link_id{
     display: inline;
    }

</style>

<body>
    <!-- Page 01 -->
    <table style="page-break-after: always;">
        <tr>
            <td colspan="3"><img src="https://nirmanbroking.com/app_themes/images/Nirman-Logo-new.png"
                    style="width: 100px;" alt=""></td>
        </tr>
        <tr>
            <td colspan="2"><b>To,</b></td>
            <td style="text-align: end;" id="date"><b>Date: ${date}</b></td>
        </tr>
        <tr>
            <td colspan="3"><b>NIRMAN SHARE BROKERS (PVT.)LTD <br> Nirman House 8,Zone-I,<br> M.P.
                Nagar, , Bhopal – 462011 (M.P.) <br> Ph.:0755-4311111,4260000.</b> <br><b>CIN
                    NO.U67120MP2001PTC14523</b></td>
        </tr>

        <tr id="link_id">
            <td>E-mail:<a href="mailto:info@nirmanbroking.com">info@nirmanbroking.com</a></td>
            <td>Website:<a href="http://nirmanbroking.com">www.nirmanbroking.com</a></td>
        </tr>
        <tr>
            <td>Depository Participant ID-12059500 SEBI REGN.IN DP- CDSL-494-2008</td>

        </tr>
        <tr>
            <td id="main-heading"><h3>ACCOUNT DETAILS ADDITION /MODIFICATION /DELETION REQUEST FORM<h3></h3></td>
        </tr>
        <tr>
            <td colspan="3">
                <b>Dear Sir/ Madam,</b>
            </td>
        </tr>
        <tr>
            <td colspan="3">I/We request you to make the following additions/modifications to my/our Broking/Demat
                accounts as
                per option selected by me/us as below.</td>
        </tr>
        <tr>
            <td colspan="2"><b>Account Detail (s) Addition/Modification Request Form</b></td>
            <td style="align-items: center;">
                DP<input type="checkbox" value="${dp}"/> TRADING <input type="checkbox" value="checked"/>

            </td>
        </tr>
        <tr>
            <td colspan="3"><br />
            </td>
        </tr>
        <tr>
            <td><b>DP ID :${dpId}</b></td>
            <td><b>CLIENT ID :${clientId}</b></td>
            <td><b>UCC : ${uccId}</b></td>
        </tr>
        <tr>
            <td colspan="3"><br />
            </td>
        </tr>
        <tr>
            <td colspan="3" style="align-items: center;">
                <b>Account Type:-</b><input type="checkbox" value="${accountTypeCheckBox}"/>Individual<input type="checkbox" value="${individulCheckBox}"/>NRI
            </td>
        </tr>
        <tr>
            <td colspan="3">
            </td>
        </tr>
        <tr>
            <td colspan="3"><b>Account Holder's Details</b></td>
        </tr>
        <tr>
            <td colspan="3">Name of First/ Sole Holder :- <input type="text" value="${firstSoleHolder}"/></td>
        </tr>
        <tr>
            <td colspan="3">Name of Second Holder :- <input type="text"/></td>
        </tr>
        <tr>
            <td colspan="3">Name of Third Holder :- <input type="text"/></td>
        </tr>
        <table>
            <tr>
                <td colspan="2">
                    <p style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                        <b style="color: white;"><input type="checkbox" value="${permanentAddCheckBox}" checked="true">Change of Permanent Address</b>
                    </p>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;"><b>Old Address Details</b></td>
                <td style="text-align: center;"><b>New Address Details</b></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Address Line 1" value="${oldAddressLine1}"/></td>
                <td><input placeholder="Address Line 1" value="${newAddressLine1}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Address Line 2" value="${oldAddressLine2}"/></td>
                <td><input placeholder="Address Line 2" value="${newAddressLine2}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="State" value="${oldState}"/></td>
                <td><input placeholder="State" value="${newState}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Pincode" value="${olgPinCode}"/></td>
                <td><input placeholder="Pincode" value="${newPinCode}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Country" value="${oldCountry}"/></td>
                <td><input placeholder="Country" value="${newCountry}"/></td>
            </tr>
        </table>
        <table>
            <tr>
                <td colspan="2">
                    <p style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                        <b style="color: white;"><input type="checkbox" value="${correspondenceAddCheckBox}" checked="true">Change of Correspondence Address</b>
                    </p>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;"><b>Old Address Details</b></td>


                <td style="text-align: center;"><b>New Address Details</b></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Address Line 1" value="${oldAddressLine1}"/></td>
                <td><input placeholder="Address Line 1" value="${newAddressLine1}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Address Line 2" value="${oldAddressLine2}"/></td>
                <td><input placeholder="Address Line 2" value="${newAddressLine2}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="State" value="${oldState}"/></td>
                <td><input placeholder="State" value="${newState}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="PinCode" value="${oldPinCode}"/></td>
                <td><input placeholder="PinCode" value="${newPinCode}"/></td>
            </tr>
            <tr style="text-align:center;">
                <td><input placeholder="Country" value="${oldCountry}"/></td>
                <td><input placeholder="Country" value="${newCountry}"/></td>
            </tr>
        </table>
    </table>
    <!-- Page 01 end-->
    <!-- Page 02 -->
    <table style="page-break-after: always;">
        <tr>
            <td>
                <table>
                    <tr>
                        <td colspan="2">
                            <p
                                style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                                <b style="color: white;"><input type="checkbox" value="${changeBankDetailCheckBox}" checked="true">Change of Bank Details</b>
                            </p>
                        </td>
                    </tr>
                    <tr style="align-items: center;">
                        <td style="text-align: center;"><b>Old Bank Details  <input type="checkbox">SB <input
                                    type="checkbox">CA<br />
                                Bank Name/ Account No./ IFSC Code/<br />
                                MICR/ Branch Name<br />
                                <input type="checkbox" checked="true" value="${oldbankDetailCheckBox}"> Default</b></td>
                        <td style="text-align: center;"><b>New Bank Details <input type="checkbox" value="${newSBCheckBox}"> SB <input
                            type="checkbox" value="${newCACheckBox}" > CA<br />
                                Bank Name/ Account No./ IFSC Code/<br />
                                MICR/ Branch Name<br />
                                <input type="checkbox"value="${isNewBankDefault}" checked="true"> Default</b></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input placeholder="Bank Name" value="${oldBankName}"/></td>
                        <td><input placeholder="Bank Name" value="${newBankName}"/></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input placeholder="Account No" value="${oldAccountNo}" /></td>
                        <td><input placeholder="Account No" value="${newAccountNo}" /></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input placeholder="IFSC Code" value="${oldIfscCode}"/></td>
                        <td><input placeholder="IFSC Code" value="${newIfscCode}"/></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input placeholder="MICR" value="${oldMicr}"/></td>
                        <td><input placeholder="MICR" value="${newMicr}"/></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input placeholder="Branch Name" value="${oldBranchName}" /></td>
                        <td><input placeholder="Branch Name" value="${newBranchName}" /></td>
                    </tr>
                    <!--  -->
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table>
                    <tr>
                        <td colspan="3">
                            <p
                                style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                                <b style="color: white;"><input type="checkbox" checked="true" value="${Updation in Annual Income Details}">Change of Email And Mobile No.</b>
                            </p>
                        </td>
                    </tr>
                    <tr style="text-align: center;">
                        <td><b>Old Mobile/ Landline No./ E-<br />mail
                                id</b>
                        </td>
                        <td><b>New Mobile/ Landline No./ E-<br />mail
                                id</b>
                        </td>
                        <td><b>Relation</b>
                        </td>
                    </tr>
                    <tr style="text-align: center;">
                        <td><input type="text" placeholder="mobile" value="${oldMobile}">
                        <td><input type="text" placeholder="mobile" value="${newMobile}">
                        </td>
                        <td><input type="text" placeholder="relation" value="Self">
                        </td>
                    </tr>
                    <tr style="text-align: center;">
                        <td><input type="text" placeholder="mobile" value="${oldEmail}">
                        <td><input type="text" placeholder="mobile" value="${newEmail}">
                        </td>
                        <td><input type="text" placeholder="relation" value="Self">
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <!-- Page 02 end -->
    <!-- Page 03 -->
    <table style="page-break-after: always;">
        <tr>
            <td>
                <table>
                    <tr>
                        <td colspan="2">
                            <p
                                style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                                <b style="color: white;"><input type="checkbox" checked="true" value="${updationInAnnualCheckBox}">Updation in Annual Income Details</b>
                            </p>
                        </td>
                    </tr>
                    <tr style="align-items: center;">
                        <td style="text-align: center;"><b>Old Annual Income details</b></td>
                        <td style="text-align: center;"><b>New Annual Income details</b></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input type="text" placeholder="Annual Income Rs.** Lac per annum "value="${oldIncomeDetails}"/></td>
                        <td><input placeholder="Annual Income Rs.** Lac per annum" value="${newIncomeDetails}" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <p
                                style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                                <b style="color: white;"><input type="checkbox" checked="true"value="${updationInNetworthCheckBox}">Updation in Networth Details</b>
                            </p>
                        </td>
                    </tr>
                    <tr style="align-items: center;">
                        <td style="text-align: center;"><b>Old Networth details</b></td>
                        <td style="text-align: center;"><b>New Networth details</b></td>
                    </tr>
                    <tr style="text-align:center;">
                        <td><input type="text" placeholder="OldNetWorth" value="${oldNetWorth}"/></td>
                        <td><input placeholder="NewNetWorth" value="${newNetWorth}" /></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table>
                    <tr>
                        <td colspan="5">
                            <p
                                style="text-align: center;font-size: 14px;background-color: rgb(10, 99, 253);padding:1% 0%;">
                                <b style="color: white;"><input type="checkbox" value="${reactivationCheckBox}" checked="true">reactivation of inactive client / Activate segment</b>
                            </p>
                        </td>
                    </tr>
                    <tr>
                            <td style="text-align: start;width: 20%;">BSE-CASH <input type="checkbox" value="${BSE}"></td>
                            <td style="text-align: start;width: 20%;">BSE-F&O <input type="checkbox" value="${BFO}"></td>
                            <td style="text-align: start;width: 20%;">BSE-CDS <input type="checkbox" value="${BCD}"></td>
                            <td style="text-align: start;width: 20%;">NSE-CASH <input type="checkbox" value="${NSE}"></td>
                            <td style="text-align: start;width: 20%;">NSE-F&O <input type="checkbox" value="${NSEFO}"></td>

                    </tr>
                    <!-- <tr>
                        <td colspan="3"></td>
                    </tr> -->
                    <tr>
                        <td colspan="5" style="width: 100%;">I/We hereby declare that the details furnished above are true and correct to the
                            best of
                            my/our
                            knowledge and belief. In case any of the above mentioned information is found to be false or
                            untrue or misleading or misrepresenting. I/We am/are aware that I/We may be held liable for
                            the
                            same. Further, I/We hereby give my/our consent to gillbroking that in future if they receive
                            my/our updated annual income details/ net-worth details, if any, then they are authorized to
                            update the same in my/our broking and/or demat account with them, in my/our records with the
                            stock exchanges, depositories and/or any other regulated entities, and/or to any regulatory
                            authorities without seeking any confirmation from me/us in this regard.
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            Note : Please share a self attested photocopy of Identity proof, Address proof, Bank proof,
                            Income & Net worth proof, as applicable.
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            In case Aadhaar card is provided for any updation then Aadhaar consent letter will be
                            required.
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            Please share the filled and duly signed KRA, CKYC & FATCA forms separately in case details
                            are
                            not updated
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            In case address proof update, KRA and CKYC forms are mandatory with the supporting
                            documents along with this form.
                        </td>
                    </tr>
                    <tr>
                        <td colspan="5">
                            1. For Address Details update please submit any one of the proofs (Self attested by client
                            and all
                            joint holders): Copy of Passport, Driving License, Proof of possession of Aadhaar number,
                            Voter's
                            Identity Card issued by Election Commission of India, Job card issued by NREGA duly signed
                            by
                            an officer of the State Government, Letter issued by the National Population Register
                            containing
                            detail of name, address, or any other document as notified by the Central Government in
                            consultation with the Regulator.<br />
                            2. For Individual account use the form to update either Correspondence or Permanent
                            address.<br />
                            3. Other than the persons specified, Mobile No./Email ID of no other persons can be updated.
                        </td>
                    </tr>


                </table>
            </td>
        </tr>

        <tr>
            <td colspan="">
                <table >
                    <tr>
                        <th style="border: 1px solid black;"></th>
                        <th style="border: 1px solid black;">First /Sole Holder</th>
                        <th style="border: 1px solid black;">Second Holder</th>
                        <th style="border: 1px solid black;">Third Holder</th>
                    </tr>
                    <tr>
                        <td style="border: 1px solid black;">Name</td>
                        <td style="border: 1px solid black;">${firstSoleHolder}</td>
                        <td style="border: 1px solid black;"></td>
                        <td style="border: 1px solid black;"></td>
                    </tr>
                    <tr>
                        <td style="border: 1px solid black;">Signature</td>
                        <td style="border: 1px solid black;">${firstSoleHolderSign}</td>
                        <td style="border: 1px solid black;"></td>
                        <td style="border: 1px solid black;"></td>
                    </tr>

                </table>
            </td>
        </tr>

        <tr>
            <td colspan=""><b>For Office Use only:-</b></td>
        </tr>
        <tr>
            <td style="text-align: start;" colspan="">
                Received By:_____${receivedBy}__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Verified By:__${verifiedBy}____&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Entered By:___${enteredBy}____
            </td>

        </tr>
        <tr><td><br><br></td></tr>

        <tr>
             <td style="text-align: end;">DP Seal & Signature &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <br><br>${dpSealAndSignature}</td>
        </tr>
        <tr><td><br><br><br></td></tr>
    </table>
    <!-- Page 03 end -->
    <!-- Page 04 start -->
     <table style="page-break-after: always;">
            <table style="page-break-after: always;">
                    <tr>
                        <td>
                            <img src="${bankProof}" width="800px" height="600px" alt="signature not available" />
                        </td>
                    </tr>
                </table>
     </table>
     <!-- Page 05 start -->
          <table style="page-break-after: always;">
                  <tr>
                      <td>
                          <img src="${financialProof}" width="800px" height="600px" alt="signature not available" />
                      </td>
                  </tr>
              </table>

              <table style="page-break-after: always;">
                      <tr>
                          <td>
                              <img src="${userImage}" width="800px" height="600px" alt="signature not available" />
                          </td>
                      </tr>
                  </table>
                <table style="page-break-after: always;">
                                        <tr>
                                            <td>
                                                <img src="${userSignature}" width="800px" height="600px" alt="signature not available" />
                                            </td>
                                        </tr>
                </table>
</body>
</html>
