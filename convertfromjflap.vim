%v/\v<from>|<to>|<read>|<write>|<move>/d
g/\<to\>/normal ddp
%s/<from>/put(new DeltaInput(State.Q/
%s/<\/from>//
%s/<to>/),new DeltaNew(State.Q
%s/<\/to>//
%s/<move>R<\/move>/, Movement.RIGHT));
%s/<move>L<\/move>/, Movement.LEFT));
%s/<write>x<\/write>/, Alphabet.X
%s/<write>y<\/write>/, Alphabet.Y
%s/<write>z<\/write>/, Alphabet.Z
%s/<write>1<\/write>/, Alphabet.ONE
%s/<write>0<\/write>/, Alphabet.ZERO
%s/<write\/>/, Alphabet.BLANK
%s/<read>x<\/read>/, Alphabet.X
%s/<read>y<\/read>/, Alphabet.Y
%s/<read>z<\/read>/, Alphabet.Z
%s/<read>1<\/read>/, Alphabet.ONE
%s/<read>0<\/read>/, Alphabet.ZERO
%s/<read\/>/, Alphabet.BLANK
%s/\n//g
%s/\<put\>/\rput/g
%sort
