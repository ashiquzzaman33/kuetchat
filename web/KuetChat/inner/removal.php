<?php require_once("db_connect.php"); 

		if(empty($_SESSION)) // if the session not yet started 
		   session_start();
		if(!isset($_POST['username'])) { // if the form not yet submitted
		   header("Location: ../index.php");
		   exit; 
}		
		$username = $_POST['username'];
		echo $username;
		$sql="DELETE FROM userinfo WHERE username='$username'";
		mysql_query($sql);
		$sql="DELETE FROM logininfo WHERE username='$username'";
		mysql_query($sql);
		header("Location: ../admin_panel.php");
		die();
		//$password = $_POST['password'];
		
		
		 


?>