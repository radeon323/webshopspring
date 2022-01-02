# Web-shop
Simple webshop based on Spring

CREATE TABLE products( <br/>
&nbsp;&nbsp;&nbsp;&nbsp;id SERIAL not NULL, <br/>
&nbsp;&nbsp;&nbsp;&nbsp;name VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;price VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;creation_date VARCHAR(50) <br/>
);
<br/><br/>

CREATE TABLE users ( <br/>
&nbsp;&nbsp;&nbsp;&nbsp;id SERIAL not NULL, <br/>
&nbsp;&nbsp;&nbsp;&nbsp;email VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;password VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;gender VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;firstName VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;lastName VARCHAR(50), <br/>
&nbsp;&nbsp;&nbsp;&nbsp;about TEXT, <br/>
&nbsp;&nbsp;&nbsp;&nbsp;age INT NOT NULL DEFAULT 0 <br/>
);
<br/><br/>