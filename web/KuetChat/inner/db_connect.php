<?php
$conn=mysql_connect('localhost','dbuser','dbpassword');
if (!$conn) {
    die('Could not connect: ' . mysqli_error($conn));
} 
mysql_select_db("kuetchatdb");
?>