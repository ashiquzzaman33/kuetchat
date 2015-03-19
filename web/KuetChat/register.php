<?php
  include 'inner/header.php';
?>
          
        <div class="article">
          <h2><span>Sign</span> Up</h2>
          <form name="reg_form" action="inner/signup_process.php" method="post" id="leavereply" onsubmit="return validateregister()">
            <ol>
              <li>
                <label for="full_name">Name*</label>
                <input id="name" name="full_name" class="text" />
              </li>
              <li>
                <label for="email">Email Address*</label>
                <input id="email" name="email" class="text" />
              </li>
              <li>
                <label for="username">User name*</label>
                <input id="email" name="username" class="text" />
              </li>
              <li>
                <label for="password">Type your password</label>
                <input type="password" id="website" name="password" class="text" />
              </li>
             <!--  <li>
                <label for="website">Re-ype your password</label>
                <input type="password" id="website" name="website" class="text" />
              </li> -->
              <li>
                <input type="image" name="imageField" id="imageField" src="images/submit.gif" class="send" />
                <!-- <input id="login_btn" type="submit" value="submit"/> -->
                <div class="clr"></div>
              </li>
            </ol>
          </form>
<?php
  if(isset($_GET['empty']))
    echo '<p style="color:red">you have to fill up all the fields in order to continue</p>';
  else if(isset($_GET['dup']))
    echo '<p style="color:red">Duplicated user name or mail address</p>';
  else if(isset($_GET['ok']))
    echo '<p style="color:green">Regestration successful</p>';
?>
        </div>
      </div>
      
      <div class="clr"></div>
    </div>
  </div>
  <div class="fbg">
    <div class="fbg_resize">
      <div class="clr"></div>
    </div>
  </div>

<?php
  include 'inner/footer.php';
?>