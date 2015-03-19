<?php
session_start();
unset($_SESSION['user_name']);
unset($_SESSION['user_type']);
session_destroy();

header("Location: ../index.php");
exit;
?>