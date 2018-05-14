<?php
	session_start();
	$_SESSION['loggedIn'] = false;
	unset($_SESSION['uname']);
	header("Location: index.php");
	exit;
?>