<?php require_once("db_connect.php"); 

		if(empty($_SESSION)) // if the session not yet started 
		   session_start();
		if(!isset($_POST['username'])) { // if the form not yet submitted
		   header("Location: ../index.php");
		   exit; 
}		
		$username = $_POST['username'];
		$password = $_POST['password'];
		
		if ( $username!="" && $password!="" ) 
		{
			$query = "SELECT * FROM logininfo NATURAL JOIN userinfo WHERE username='$username' AND password='$password'";
			$result_set = mysql_query($query);
			$found_user=mysql_fetch_array($result_set);
			if (mysql_num_rows($result_set) == 1) 
			{
				$_SESSION['user_type']=$found_user['usertype'];
				echo $found_user['block'];
				if($found_user['block']==1)
				{
					session_destroy();
					header("Location: ../index.php?b=1");	
					die();				
				}
				$_SESSION['user_name'] = $username;
				header("Location: ../index.php");

			} 
			else 
			{
				session_destroy();
				header("Location: ../index.php?error=yes");
			}
		} 
		 


?>