<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >


    <xsl:template match="/MyMsg">
        <html>
	<head>
	
	     <link rel="stylesheet" type="text/css" href="bubble.css"/>

	</head>
            <body>
                <div>
                    <center>
                    <h1>
                <xsl:value-of select="Header"/>
                  <xsl:variable name="saver" select="Header"/>

                </h1>
                </center>
                </div>
              
                 <xsl:for-each select="Msg">
                     
                  <br>
            </br>
             <br>
            </br>
            <br>
            </br>
            
                      <div >
                                <xsl:choose>
				<xsl:when test="me = 'true'">

                                    <xsl:attribute name="class">bubble you</xsl:attribute>

                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:attribute name="class">bubble me</xsl:attribute>
                                </xsl:otherwise>
                                
				</xsl:choose>
                         <xsl:value-of select="from"/>   
<br>
</br>
   
                   <font >  
                                                           

                       <xsl:attribute name="size">
                             <xsl:value-of select="@size"/>
                          </xsl:attribute>
                          
                        <xsl:attribute name="style">
                             <xsl:value-of select="@color"/>
                          </xsl:attribute>
                          <xsl:attribute name="face">
                             <xsl:value-of select="@font"/>
                          </xsl:attribute>
                        
                        
                        

		
		      <xsl:value-of select="body"/>			

						                 
                
                   </font>
 <br>
					   </br>
					   				<font size="1"> <xsl:value-of select="date"/></font>


                       </div> 
					  
                   
                    </xsl:for-each>
                
             
                                    
 </body>
        </html>
    </xsl:template>

</xsl:stylesheet>