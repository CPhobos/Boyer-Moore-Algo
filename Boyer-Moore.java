import java.util.Scanner;

class boyer_moore {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an input string: ");
        String input_string = scanner.next();
        System.out.println("Enter a pattern: ");
        String target_string = scanner.next();
        //System.out.println(String.format(""));
        int index = index_of(input_string.toCharArray(), target_string.toCharArray());
        String result = index != -1 ? "Found At %s" : "%s Not Found";
        System.out.println(String.format(result, index));
    }

    public static int index_of(char[] input_string, char[] target_string){
        if(target_string.length == 0){
            return 0;
        }
        int char_table[] = construct_char_table(target_string);
        int offet_table[] = construct_offset_table(target_string);
        for(int i = target_string.length - 1, j; i < input_string.length;){
            for(j = target_string.length - 1; target_string[j] == input_string[i]; --i, --j){ 
                if(j == 0){
                    return i;
                }
            }
            i += Math.max(offet_table[target_string.length - 1 - j], char_table[input_string[i]]);
        }
        return -1;
    }

    private static int[] construct_char_table(char[] target_string){
        final int alphabet_size = Character.MAX_VALUE + 1;
        int[] table = new int[alphabet_size];
        for(int i = 0; i < table.length; ++i){
            table[i] = target_string.length;
        }
        for(int i = 0; i < target_string.length; ++i){
            table[target_string[i]] = target_string.length - 1 - i;
        }
        return table;
    }

    private static int[] construct_offset_table(char[] target_string){
        int[] table = new int[target_string.length];
        int last_index = target_string.length;
        for(int i = target_string.length; i > 0; --i){
            if(is_this_char_a_prefix(target_string, i)){
                last_index = i;
            }
            table[target_string.length - i] = last_index - i + target_string.length;
        }
        for (int i = 0; i < target_string.length - 1; ++i){
            int len  = get_suffix_length(target_string, i);
            table[len] = target_string.length - 1 - i + len;
        }
        return table;
    }

    private static boolean is_this_char_a_prefix(char[] target_string, int p){
        for(int i = p, j = 0; i < target_string.length; ++i,++j){
            if(target_string[i] != target_string[j]){
                return false;
            }
        }
        return true;
    }
    
    private static int get_suffix_length(char[] target_string, int p){
        int len = 0;
        for(int i = p, j = target_string.length - 1; i >= 0 && target_string[i] == target_string[j]; --i, --j){
            len += 1;
        }
        return len;
    }
}
