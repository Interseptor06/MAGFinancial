# MAGFinancial
MAG Financial is a project I and George Kolev are working on for Innovation Starter Academy - a hackathon and business development contest. 
Since we're on a tight time limit documentation will be worked on after the competition and we estimate a couple of weeks before it's finalized.

Update(13.06.2022):
So, the hackathon ended and I'm happy to say that we won 3rd place. Considering the hackathon was intended for university students and we're
in 10th grade I'd say we did pretty good. So right now the code in the repo is v1 of the project. Since I hate leaving code in this state over 
the next few weeks I'll be finishing the Limit Order Book and Matching Engine implementation and finishing the StockExchange Package. To this end I will 
be adding support for OUCH messages instead of my naive message format:) . Also during this time I read quite a lot about writing a good parser and will
be entirely reworking what is now NaiveMessageParser. Along with this the application will probably become multithreaded and possibly multi-processed in order to decouple systems and allow for more reliability. 

One more thing I'd like to say is that my colleuege and I are considering a migration from Java to C# or possibly C++. The transition to C++ would give 
us much more fine-grained control over things like memory management, multithreading and networking. C# on the other hand would get us a working
prototype much much faster(especially considering that both of us have entensive experience in C# development and system design). 

One the other hand we have the Banking Package, whose architecture we have completed and will implement after we chose the platform to which we will 
migrate. Luckily though with a well thought out system architecture and integration between the exchange and banking platform the development process
shouldn't be as difficult.

There is a large chance that the post-migration repo will stay private for a while until we get a running, tested and working version.

For any questions you can message me on linkedin: https://www.linkedin.com/in/martin-chtilianov-29b6b8236
or on my email: martin@chtilianov.com
