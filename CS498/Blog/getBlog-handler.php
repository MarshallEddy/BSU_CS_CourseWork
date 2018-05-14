<?php
session_start();
require_once('Dao.php');

$dao = new Dao();

$blogPosts = $dao->getBlogPosts();
$blogTitles = $dao->getBlogTitles();

setcookie('blogPosts', serialize($blogPosts));
setcookie("blogTitles", serialize($blogTitles));
$_SESSION['gotBlogs'] = true;
header("Location: index.php");
exit;

?>