# DataGetter
Gets data from provided url using post
Creating a executable jar from Maven is pretty straight forward and involves using the maven-assembly-plugin. This can be configured and added to your pom.xml as below:
 <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.example.Main</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                        <execution>
                            <phase>install</phase>
                            <goals>
                                <goal>single</goal>
                            </goals>
                        </execution>
                </executions>
            </plugin>
We need to add one more plugin for the above to work
<plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-release-plugin</artifactId>
      <version>2.5.1</version>
      <configuration>
           <goals>install</goals>
           <preparationGoals>install</preparationGoals>
      </configuration>
</plugin>
