import java.io.*;

class Tables{
    String name;
    int address;
    Tables(String name, int address){
        this.name = name;
        this.address = address;
    }
}

class Pooltable{
    int first, total_literals;
    Pooltable(int f, int tl){
        first = f;
        total_literals = tl;
    }
}

public class Pass1 {
    public static int search(String token, String[] list) {
        for(int i = 0; i < list.length; i++)
            if(token.equalsIgnoreCase(list[i]))
                return i;
        return -1;
    }
    public static int search(String token, Tables[] list, int cnt) {
        for(int i = 0; i < cnt; i++)
            if(token.equalsIgnoreCase(list[i].name))
                return i;
        return -1;
    }

    public static void main(String[] args) throws IOException{
        String[] registers = {"AX","BX","CX","DX"};
        String[] impr = {"STOP","ADD","SUB","MULT","MOVER","MOVEM","COMP","BC","DIV","READ","PRINT"};
        String[] decl = {"DS","DC"};
        Tables[] op_table = new Tables[50];
        Tables[] symbol_table = new Tables[20];
        Tables[] literal_table = new Tables[20];
        Pooltable[] poolTab = new Pooltable[10];
        String line;
        try{
            BufferedReader br = new BufferedReader(new FileReader("assembly.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("intermediatecode.txt"));
            Boolean start = false, end = false, ltorg = false, fill_addr = false;
            int total_symb=0, total_ltr=0, optab_cnt=0, pooltab_cnt=0, loc=0, temp,pos;
            while((line = br.readLine()) != null && !end) {
                String[] words = line.split(" ");
                if (loc != 0 && !ltorg) {
                    if (line.contains("DS")) {
                        bw.write("\n" + String.valueOf(loc));
                        loc += Integer.parseInt(words[2]);
                    } else bw.write("\n" + String.valueOf(loc++));
                }
                ltorg = fill_addr = false;
                for (int i = 0; i < words.length; i++) {
                    pos = -1;
                    if (start == true) {
                        loc = Integer.parseInt(words[i]);
                        start = false;
                    }
                    switch (words[i]) {
                        case "START":
                            start = true;
                            pos = 1;
                            bw.write("\t(AD," + pos + ")");
                            break;
                        case "END":
                            end = true;
                            pos = 2;
                            bw.write("\t(AD," + pos + ")");
                            break;
                        case "ORIGIN":
                            pos = 3;
                            bw.write("\t(AD," + pos + ")");
                            pos = search(words[++i], symbol_table, total_symb);
                            i++;
                            bw.write("\t(S," + (pos + 1) + ")");
                            loc = symbol_table[pos].address;
                            break;
                        case "EQU":
                            pos = 4;
                            bw.write("\t(AD," + pos + ")");
                            String prev_word = words[i-1];
                            int pos1 = search(prev_word, symbol_table, total_symb);
                            pos = search(words[++i], symbol_table, total_symb);
                            symbol_table[pos1].address = symbol_table[pos].address;
                            bw.write("\t(S," + (pos + 1) + ")");
                            break;
                        case "LTORG":
                            ltorg = true;
                            pos = 5;
                            for (temp = 0; temp < total_ltr; temp++) {
                                if (literal_table[temp].address == 0) {
                                    literal_table[temp].address = loc - 1;
                                    bw.write("\t(DL,1)\t(L," + (temp + 1) + ")" + "\n" + loc++);
                                }
                            }
                            if (pooltab_cnt == 0)
                                poolTab[pooltab_cnt++] = new Pooltable(0, temp);
                            else {
                                poolTab[pooltab_cnt] = new Pooltable(poolTab[pooltab_cnt - 1].first + poolTab[pooltab_cnt - 1].total_literals, total_ltr - poolTab[pooltab_cnt - 1].first - 1);
                                pooltab_cnt++;
                            }
                            break;
                    }
                    if (pos == -1) {
                        pos = search(words[i], impr);
                        int r = search(words[i],registers);
                        if (pos != -1) {
                            bw.write("\t(IS," + pos + ")");
                            op_table[optab_cnt++] = new Tables(words[i], pos);
                        } else {
                            pos = search(words[i], decl);
                            if (pos != -1) {
                                bw.write("\t(DL," + (pos + 1) + ")");
                                op_table[optab_cnt++] = new Tables(words[i], pos);
                                fill_addr = true;
                            } else if (words[i].matches("[a-zA-Z]+") && r==-1) {
                                pos = search(words[i], symbol_table, total_symb);
                                if (pos == -1) {
                                    symbol_table[total_symb++] = new Tables(words[i].substring(0, words[i].length()), loc - 1);
                                    bw.write("\t(S," + total_symb + ")");
                                    pos = total_symb;
                                }
                            }
                        }
                    }
                    if (pos == -1) {
                        pos = search(words[i], registers);
                        if(pos!=-1)
                            bw.write("\t("+(pos+1)+")");
                        else{
                            if(words[i].matches("='\\d+'")){
                                literal_table[total_ltr++] = new Tables(words[i],0);
                                bw.write("\t(L,"+total_ltr+")");
                            }
                            else if(words[i].matches("\\d+") || words[i].matches("\\d+H") || words[i].matches("\\d+h"))
                                bw.write("\t(C,"+words[i]+")");
                            else{
                                pos = search(words[i],symbol_table,total_symb);
                                if(fill_addr && pos != -1){
                                    symbol_table[pos].address = loc-1;
                                    fill_addr = false;
                                }
                                else if(pos==-1){
                                    symbol_table[total_symb++] = new Tables(words[i],0);
                                    bw.write("\t(S,"+total_symb+")");
                                }
                                else bw.write("\t(S,"+pos+")");
                            }
                        }
                    }
                }
            }
            br.close();
            bw.close();
            
            BufferedWriter sw = new BufferedWriter(new FileWriter("symtab.txt"));
            for(int i=0;i<total_symb;i++)
                sw.write(symbol_table[i].name+"\t\t"+symbol_table[i].address+"\n");
            sw.close();

            BufferedWriter lw = new BufferedWriter(new FileWriter("littab.txt"));
            for(int i=0;i<total_ltr;i++)
            {
                if(literal_table[i].address==0)
                    literal_table[i].address=loc++;
                lw.write((i+1) +"\t"+literal_table[i].name+"\t"+literal_table[i].address+"\n");
            }
            lw.close();

            BufferedWriter pw = new BufferedWriter(new FileWriter("pooltab.txt"));
            for(int i=0;i<pooltab_cnt;i++)
                pw.write(poolTab[i].first+"\t"+poolTab[i].total_literals+"\n");
            pw.close();

            BufferedWriter mw = new BufferedWriter(new FileWriter("motab.txt"));
            for(int i=0;i<optab_cnt;i++)
                mw.write(op_table[i].name+"\t\t"+op_table[i].address+"\n");
            mw.close();

        }catch(Exception e){
            System.out.println("Error occured while reading file\n");
            e.printStackTrace();
        }
    }
}
