<?php
session_start();
require_once('Dao.php');

function trim_value(&$value) {
	$value = trim($value);
}
array_filter($_POST, 'trim_value');

$blogID = $_POST['commentBoxID'];



$comment = $_POST['commentBox'];
$uname = $_SESSION['uname']; 
$blog = $_SESSION['blog'];


$blog = $_SESSION['blog'][$blogID];

$_SESSION['comment'] = $comment;

$dao = new Dao();


$_SESSION['commented'][$blogID] = true;

$dao->saveComment($blog, $uname, $comment);
$_SESSION['gettingComment'] = false;
unset($_SESSION['gettingComment']);
header(('Location: blogs.php#commentHeader' . $blogID));
?>