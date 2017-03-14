
class SimpleHello(object):

    def setParameters(self, token):
        self.token = token

    def sayHello(self):
        return self.token + "Said hello"

    class Java:
        implements = ["com.py4jdemo.IHello"]

# Make sure that the python code is started first.
# Then execute: java -cp py4j.jar py4j.examples.SingleThreadClientApplication
from py4j.java_gateway import JavaGateway, CallbackServerParameters,GatewayParameters
import sys
def quit(gateway):
    print 'You choose to stop me.'
    gateway.shutdown()
    sys.exit()


simple_hello = SimpleHello()
gateway = JavaGateway(
    gateway_parameters=GatewayParameters(address='127.0.0.1'),
    callback_server_parameters=CallbackServerParameters(),
    python_server_entry_point=simple_hello)


str = raw_input("Enter your input: ");
quit(gateway)



