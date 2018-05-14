<?php
session_start();
require_once('Dao.php');

function trim_value(&$value) {
	$value = trim($value);
}
array_filter($_POST, 'trim_value');

$uname = $_POST['uname'];
$psw = $_POST['psw'];

// Sanitize Data
$invalid_char = array("$", "%", "#", "<", ">", "|", "=", "&", "{", "}", "*", "+");
$uname = str_replace($invalid_char, "", $uname);
$psw = str_replace($invalid_char, "", $psw);

$_SESSION['uname'] = $uname;
$_SESSION['psw'] = $psw;
$err = array();

setcookie('unamel', $uname);

$dao = new Dao();

// if (strlen(trim($uname)) == 0) {
//     $err['uname'] = "Please Enter a user name.";
// }
// if (strlen(trim($psw)) == 0) {
//     $err['psw'] = "Please Enter a psw.";
// }

if (empty($err)) {
    if ($dao->userExists($uname)) {
        if ($dao->loginUser($uname, $psw)) {
            unset($_SESSION["psw"]);
            $_SESSION['loggedIn'] = true;
            unset($_SESSION['errors']);
            unset($_SESSION['presets']);
            header('Location: myAccount.php');
        } else {
            $err['psw'] = "Incorrect password";
            $_SESSION['errors'] = $err;
            $_SESSION['invalidLogin'] = true;
            header("Location: " . $_SESSION['currentPage']);
        } 
    } else {
        $err['uname'] = "Invalid user name.";
        $_SESSION['invalidLogin'] = true;
        $_SESSION['errors'] = $err;
        header("Location: " . $_SESSION['currentPage']);
    }
} else {
    $_SESSION['errors'] = $err;
    $_SESSION['presets'] = array('uname' => htmlspecialchars($uname));
    $_SESSION['invalidLogin'] = true;
    header("Location: " . $_SESSION['currentPage']);
}
?>