<?php
session_start();
require_once('Dao.php');

function trim_value(&$value) {
	$value = trim($value);
}
array_filter($_POST, 'trim_value');

$email = $_POST['email'];
$uname = $_POST['uname'];
$psw = $_POST['psw'];
$psw_match = $_POST['psw_match'];

$_SESSION["uname"] = $uname;
$_SESSION["psw"] = $psw;
$_SESSION["email"] = $email;

// Sanitize Data
$invalid_char = array("$", "%", "#", "<", ">", "|", "=", "&", "{", "}", "*", "+");
$uname = str_replace($invalid_char, "", $uname);
$psw = str_replace($invalid_char, "", $psw);
$psw_match = str_replace($invalid_char, "", $psw_match);
$email = filter_var($email, FILTER_SANITIZE_EMAIL);

$errors = array();

setcookie('uname', $uname);
setcookie('email', $email);

$display = array(
	'uname' => $uname,
	'email' => $email
);



$dao = new Dao();

function valid_length($field, $min, $max) {
	$trimmed = trim($field);
	return (strlen($trimmed) >= $min && strlen($trimmed) <= $max);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	foreach ($_POST as $key => $val) {
		if (isset($display[$key])) {
			$display[$key] = htmlspecialchars($val);
		}
	}
}
	
if(!valid_length($uname, 8, 20)) {
	$errors['uname'] = "A valid user name is required. Must be between 8 and 20 characters.";
}
if(!valid_length($email, 1, 50)) {
	$errors['email'] = "Email is required. Must be less than 50 characters.";
} 
else if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
	$errors['email'] = "Must be a valid email address.";
}

$uppercase = preg_match('@[A-Z]@', $psw);
$lowercase = preg_match('@[a-z]@', $psw);
$number    = preg_match('@[0-9]@', $psw);

if(!$uppercase || !$lowercase || !$number || strlen($psw) < 8) {
	$errors['psw'] = "Password must contain at least one uppercase, lowercase, and number. Password must also be at least 8 characters.";
} 

else if($psw != $psw_match) {
	$errors['psw_match'] = "Passwords much match.";
}

if(empty($errors)) {
	if(!$dao->userExists($uname)) {
		$dao->addUser($uname, $psw, $email);
		$_SESSION['loggedIn'] = true;
		unset($_SESSION["psw"]);
		$_SESSION['uname'] = htmlspecialchars($uname);
		header('Location: index.php');
	} else {
		$errors['email'] = "This email already exists, please log in.";
		header('Location: createAccount.php');
	}
} else {
	$_SESSION['errors'] = $errors;
	$_SESSION['presets'] = array('uname' => htmlspecialchars($uname),
					'email' => htmlspecialchars($email));
	header('Location: createAccount.php');
}

?>