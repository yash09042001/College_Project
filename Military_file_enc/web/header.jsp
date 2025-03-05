<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Storage System</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/font-awesome.css" rel="stylesheet">
        <link href="css/validationEngine.css" rel="stylesheet">
        <link href="css/stye.css" rel="stylesheet">
        <script src="js/jquery-1.7.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.validationEngine-en.js"></script>
        <script src="js/jquery.validationEngine.js"></script>
        <script src="js/highcharts.js"></script>
        <script src="js/data.js"></script>
        <script src="js/script.js"></script>

        <script>
            function ClearFields() {

                document.getElementById("textfield1").value = "";
                document.getElementById("textfield2").value = "";
            }
            function phonenumber(inputtxt)
            {
                var phoneno = /^\d{10}$/;
                if (inputtxt.value.match(phoneno))
                {
                    return true;
                }
                else
                {
                    alert("Not a valid Phone Number");
                    return false;
                }
            }
            function loadFile()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='file_upload.jsp' style='width: 100%; height:500px;'></iframe>";
            }
            function loadimage()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='image_upload.jsp'  style='width: 100%; height:500px'></iframe>";
            }
            function loaddata1()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='viewfile.jsp?data=img' style='width: 100%;height:500px'></iframe>";
            }
            function loaddata()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='viewfile.jsp?data=file' style='width: 100%;height:500px'></iframe>";
            }
            function loaddata_users()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='viewfile.jsp?data=file' style='width: 100%;height:500px'></iframe>";
            }
            function recovery()
            {
                document.getElementById("dynamic").innerHTML = "<iframe src='recover.jsp?data=file' style='width: 100%;height:500px'></iframe>";
            }
            var _validFileExtensions = [".jpg", ".jpeg", ".bmp", ".gif", ".png", ".pdf", ".docx", ".txt"];
            function Validate(oForm) {

                var arrInputs = oForm.getElementsByTagName("input");
                for (var i = 0; i < arrInputs.length; i++) {
                    var oInput = arrInputs[i];
                    if (oInput.type == "file") {
                        var sFileName = oInput.value;
                        if (sFileName.length > 0) {
                            var blnValid = false;
                            alert("invalid file" + blnValid);
                            for (var j = 0; j < _validFileExtensions.length; j++) {
                                var sCurExtension = _validFileExtensions[j];
                                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                                    blnValid = true;
                                    alert("valid file" + blnValid);
                                    break;
                                }
                            }

                            if (!blnValid) {
                                alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        </script>
    </head>
    <body>