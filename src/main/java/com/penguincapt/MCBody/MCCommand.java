package com.penguincapt.MCBody;

import com.penguincapt.MessagePurposeType;

public class MCCommand extends MCBody {
    CommandOrigin origin;
    String commandLine;
    int version;
    public MCCommand(String originType,String commandLine,int version) {
        super(MessagePurposeType.COMMAND_REQUEST);
        this.origin = new CommandOrigin(originType);
        this.commandLine = commandLine;
        this.version = version;
    }
    private static class CommandOrigin{
        private String type;
        public CommandOrigin(String type) {
            this.type = type;
        }
    }

}
