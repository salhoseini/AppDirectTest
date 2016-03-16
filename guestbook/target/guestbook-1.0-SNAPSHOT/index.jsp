<%@ page session="true" %>
<html>
    <head>
        <style type="text/css">
            body: {
                background: darkslategrey;
                font-family: Helvetica;
            }

            form: {
                margin: 0px;
            }

            .content {
                display: block;
                margin: 0 auto;
                height: 100%;
                width: 200px;
                text-align: center;
                vertical-align: middle;
            }
            .inner-container {
                position: absolute;
                top: 50%;
                height: 200px;
                margin-top: -100px;
                left: 50%;
                margin-left: -100px;
                width: 200px;
            }

            .inner-container label {
                color: white;
                font-size: 20px;
            }

            .inner-container input {
                height: 100px;
                width: 200px;
                font-size: 36px;
                font-weight: bold;
                border-collapse: collapse;
                border: none;
                background: brown;
                color: white;
                border-radius: 10px;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
       <form action="/login?identifier=https://www.appdirect.com/openid/id" method="post">
            <div class="content">
                <div class="inner-container">
                    <label>
                        Login with AppDirect
                    </label>
                    <input type="submit" value="Login">
                </div>
            </div>
       </form>
    </body>
</html>