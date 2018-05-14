<?php
session_start();
require_once('Dao.php');

$dao = new Dao();

$n = count($_SESSION['blog']);

$getUname = array();
$getComment = array();
$getDateTime = array(); 

$scrollToC = 0;
$scrollToB = 0;

$i = 0;
for ($i; $i <= $n; $i++) {
    //echo $_SESSION['blog'][$i + 1];
    array_push($getUname, $dao->getUname($_SESSION['blog'][$i]));
    array_push($getComment, $dao->getComment($_SESSION['blog'][$i]));
    array_push($getDateTime, $dao->getDateTime($_SESSION['blog'][$i]));
    if ($_SESSION['commented'][$i] == true) {
        $scrollToC = $i;
    } else if ($_SESSION['indexRedirect'][$i] == true) {
        $scrollToB = $i;
    }
}

$_SESSION['commUname'] = $getUname;
$_SESSION['comm'] = $getComment;
$_SESSION['commDateTime'] = $getDateTime;

$_SESSION['gettingComment'] = true;

unset($_SESSION['commented']);
unset($_SESSION['indexRedirect']);

if ($scrollToC == 0 && $scrollToB == 0) {
    header("Location: blogs.php");
} else if ($scrollToB == 0 || $scrollToC > 0) {
    header("Location: blogs.php#commentHeader" . $scrollToC);
} else if ($scrollToC == 0 || $scrollToB > 0) {
    header("Location: blogs.php#blog" . $scrollToB);
}
?>