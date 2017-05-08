<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Login Page</title>
</head>
<body>
<h3>Login with Username and Password-ci</h3>
<form name='f' action='/login/show' method='post'>
	<input type="hidden" name="${(_csrf.parameterName)!''}" value="${(_csrf.token)!''}"/>
	<table>
		<tr>
			<td>User:</td>
			<td><input type='text' name='username' value=''></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='password'/></td>
		</tr>
		<tr>
			<td colspan='2'><input name="submit" type="submit" value="Login"/></td>
		</tr>
	</table>
</form>
</body>
</html>