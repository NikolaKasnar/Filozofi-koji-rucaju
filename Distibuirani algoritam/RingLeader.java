public class RingLeader extends Process implements Election {
    int number;    
    int leaderId = -1;
    int next, prev, max;   
    boolean awake = false;
    boolean blocked = false;

    public RingLeader(Linker initComm, int number) {
        super(initComm);
        this.number = number;
        next = (myId + 1) % N;
        prev = myId > 0 ? (myId - 1) : N-1;
        max = -1;
    }

    public synchronized int getLeader(){
	    while (leaderId == -1) myWait();
	    return leaderId;
    }

    public synchronized void handleMsg(Msg m, int src, String tag) {
        int j = m.getMessageInt(); // get the number
        if (tag.equals("election")) {
            if (j < number || blocked)
                sendMsg(next, "election", j); // forward the message
            else if (j == number) // I won!
                sendMsg(next, "leader", myId);
            //else if ((j > number) && !awake) startElection();
        } else if (tag.equals("leader")) {
            leaderId = j;
	    notify();
            if (j != myId) {
                sendMsg(leaderId, "time", number);
                sendMsg(next, "leader", j);
                if(j != next && j != prev) {  blocked = false; startElection(); }
                else blocked = true;
            } else {
                number += max;
                awake = false;
                Util.println("number = " + number);
            }
        } else {
            if ( j > max ) max = j;
        }
    }

    public synchronized void startElection() {
        awake = true;
        sendMsg(next, "election", number);
    }
}
