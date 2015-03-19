		function validateForm()
		{
			var x=document.forms["upload_form"]["course"].value;
			if (x==null || x=="")
			{
			  alert("Course title must be filled out");
			  return false;
			}
			return true;
		}
	
		function selectcourse()
		{
			var str=document.getElementById("yearselect").value;
			/*document.write("baal chaal"+option);*/


		if (str.length==0)
		  { 
		  document.getElementById("txtHint").innerHTML="";
		  return;
		  }
		var xmlhttp=new XMLHttpRequest();
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("course").innerHTML=xmlhttp.responseText;
		    }
		  }
		xmlhttp.open("GET","processing/course_return.php?q="+str,true);
		xmlhttp.send();
		}


		function validateCt()
		{
			var x=document.forms["ct_form"]["year"].value;
			if (x==null || x=="0")
			{
			  alert("year must be filled out");
			  return false;
			}
            x=document.forms["ct_form"]["teacher_name"].value;
			if (x==null || x=="")
			{
			  alert("teacher_name marks must be filled out");
			  return false;
			}

			if (document.getElementById('ct_no').value=="0")
			{
			  alert("ct no must be filled out");
			  return false;
			}

			

			x=document.forms["ct_form"]["full_marks"].value;
			if (x==null || x=="")
			{
			  alert("full marks must be filled out");
			  return false;
			}



			x=document.forms["ct_form"]["date"].value;
			if (x==null || x=="")
			{
			  alert("date must be filled out");
			  return false;
			}	
			
			x=document.forms["ct_form"]["time"].value;
			if (x==null || x=="")
			{
			  alert("time must be filled out");
			  return false;
			}

		}