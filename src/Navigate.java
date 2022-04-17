import javafx.beans.binding.BooleanExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Navigate {

    public static void main(String[] args) throws IOException {
        
        Set<String> leftSide = populatePeopleToCross();
        Set<String> rightSide = new HashSet<>();

        System.out.println("\n\n\n------------------------------------------------------------------");
        System.out.println("Que comecem os jogos");
        System.out.println("Quando você desejar que o acompanhante seja vazio, tecle apenas enter");
        System.out.println("Para mover as pessoas, apenas digitar o nome como o da lista em RULES");
        System.out.println("---------------------------------------------------------------------");

        while (leftSide.size() != 0) {
            crossLeftToRight(leftSide, rightSide);
            if (rightSide.size() == 8){
                break;
            }
            crossRightToLeft(leftSide, rightSide);
        }

        System.out.println("Parabéns, você é um campeão!");
    }

    private static void crossRightToLeft(Set<String> leftSide, Set<String> rightSide) throws IOException {
//        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        printRulesAndPeople(rightSide);
        System.out.println("Quem vai operar o bote?");
        String chosenOperator = reader.readLine();
        System.out.println("Quem vai acompanhar o operador do bote?");
        String companionChosen = reader.readLine();
        validateIfPeopleChosenCanCross(chosenOperator, companionChosen);
        boolean canCross = validateIfPeopleChosenCanCross(chosenOperator, companionChosen);

        if (canCross) {
            movePeople(leftSide, rightSide, chosenOperator, companionChosen, true, false);
        } else {
            System.out.println("\n\n\n------------------------------------------------------------------");
            System.out.println("As pessoas selecionadas não podem ser movidas da forma que escolheu :(");
            System.out.println("------------------------------------------------------------------");
            crossRightToLeft(leftSide, rightSide);
        }

//        sc.close();
    }

    private static void crossLeftToRight(Set<String> leftSide, Set<String> rightSide) throws IOException {
//      Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        printRulesAndPeople(leftSide);
        System.out.println("Quem vai operar o bote?");
        String chosenOperator = reader.readLine();
        System.out.println("Quem vai acompanhar o operador do bote?");
        String companionChosen = reader.readLine();

        boolean canCross = validateIfPeopleChosenCanCross(chosenOperator, companionChosen);

        if (canCross) {
            movePeople(leftSide, rightSide, chosenOperator, companionChosen, false, true);
        } else {
            System.out.println("\n\n\n------------------------------------------------------------------");
            System.out.println("As pessoas selecionadas não podem ser movidas da forma que escolheu :(");
            System.out.println("------------------------------------------------------------------");
            crossLeftToRight(leftSide, rightSide);
        }


    }

    private static void movePeople(Set<String> leftSide, Set<String> rightSide, String chosenOperator, String companionChosen, Boolean rightToLeft, Boolean leftToRight) throws IOException {
        Set<String> peopleToMove = new HashSet<>();
        peopleToMove.add(chosenOperator);
        peopleToMove.add(companionChosen);

        if(rightToLeft == true){
            rightSide.removeAll(peopleToMove);
            leftSide.addAll(peopleToMove);
            rightSide.remove("");
        }
        if (leftToRight == true){
            leftSide.removeAll(peopleToMove);
            rightSide.addAll(peopleToMove);
            leftSide.remove("");
        }

        Boolean hasError = validateBothSides(leftSide, rightSide);
        if(hasError){
            if(rightToLeft){
                leftSide.removeAll(peopleToMove);
                rightSide.addAll(peopleToMove);
                crossRightToLeft(leftSide, rightSide);
            }
            if (leftToRight){
                rightSide.removeAll(peopleToMove);
                leftSide.addAll(peopleToMove);
                crossLeftToRight(leftSide, rightSide);
            }
        }

    }

    private static Boolean validateBothSides(Set<String> leftSide, Set<String> rightSide) {
        if(rightSide.size() == 3 && (rightSide.contains("mae") && rightSide.contains("filho1") &&  (rightSide.contains("filho2"))) ||
        (rightSide.size() == 2 && ((rightSide.contains("mae") && rightSide.contains("filho1")) ||  (rightSide.contains("mae") && rightSide.contains("filho2"))))){
            System.out.println("-------------------------------------------------------------");
            System.out.println("A mãe ficou sozinha com os filhos do lado direito");
            System.out.println("----------------Retornando sua jogada----------------------");
            return true;
        }
        else if((leftSide.size() == 3 && (leftSide.contains("mae") && leftSide.contains("filho1") && leftSide.contains("filho2"))) ||
                (leftSide.size() == 2 && ((leftSide.contains("mae") && leftSide.contains("filho1")) ||  (leftSide.contains("mae") && leftSide.contains("filho2"))))){
            System.out.println("-------------------------------------------------------------");
            System.out.println("A mãe ficou sozinha com os filhos do lado esquerdo");
            System.out.println("----------------Retornando sua jogada----------------------");
            return true;
        }
        else if((leftSide.size() == 3 && (leftSide.contains("pai") && (leftSide.contains("filha1") && leftSide.contains("filha2")))) ||
        (leftSide.size() == 2 && ((leftSide.contains("pai") && leftSide.contains("filha1")) ||  (leftSide.contains("pai") && leftSide.contains("filha2"))))){
            System.out.println("-------------------------------------------------------------");
            System.out.println("A pai ficou sozinho com as filhas do lado esquerdo");
            System.out.println("----------------Retornando sua jogada----------------------");
            return true;
        }
        else if((rightSide.size() == 3 && (rightSide.contains("pai") && (rightSide.contains("filha1") && rightSide.contains("filha2")))) ||
        (rightSide.size() == 2 && ((rightSide.contains("pai") && rightSide.contains("filha1")) ||  (rightSide.contains("pai") && rightSide.contains("filha2"))))){
            System.out.println("-------------------------------------------------------------");
            System.out.println("A pai ficou sozinho com as filhas do lado direito");
            System.out.println("----------------Retornando sua jogada----------------------");
            return true;
        }
        else if (leftSide.size() == 2 && (leftSide.contains("pai") && leftSide.contains("mae"))){
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Pense melhor na sua jogada, dessa forma quem voltará buscar os pais? ");
            System.out.println("-------------------------Retornando sua jogada----------------------");
            return true;
        }
        return false;
    }

    private static void printRulesAndPeople(Set<String> side) {
        System.out.println("\n--------------------------Rules-----------------------------");
        System.out.println("O bote carrega duas pessoas por vez" + "\nApenas a mãe, o pai e o policial pode operar o bote" + "\nA mãe não pode ser deixada sozinha com os filhos" +
                        "\nO pai não pode ser deixado sozinho com as filhas" + "\nO ladrão não pode ser deixado sozinho sem o policial");
        System.out.println("------------------------------------------------------------");
        
        for (String person: side){
            System.out.println(person);
        }
        System.out.println("-------------------------------------------------------------");
    }


    private static boolean validateIfPeopleChosenCanCross(String chosenOperator, String companionChosen) {
        peopleWhoCanOperateBote().contains(chosenOperator);
        peopleWhoCanFollow().contains(companionChosen);
        boolean copAndThiefAreTogether;

        if (chosenOperator.equals("policial") || companionChosen.equals("ladrao")){
            copAndThiefAreTogether = chosenOperator.equals("policial") && companionChosen.equals("ladrao");
        } else {
            copAndThiefAreTogether = true;
        }

        return peopleWhoCanOperateBote().contains(chosenOperator) &&
        peopleWhoCanFollow().contains(companionChosen) && copAndThiefAreTogether;
    }

    private static Set<String> peopleWhoCanFollow() {
        Set<String> canFollowTheOperator = new HashSet<>();

        canFollowTheOperator.add("filho1");
        canFollowTheOperator.add("filho2");
        canFollowTheOperator.add("filha1");
        canFollowTheOperator.add("filha2");
        canFollowTheOperator.add("ladrao");
        canFollowTheOperator.add("pai");
        canFollowTheOperator.add("mae");
        canFollowTheOperator.add("");

        return canFollowTheOperator;
    }

    private static Set<String> peopleWhoCanOperateBote() {
        Set<String> canOperateBote = new HashSet<>();

        canOperateBote.add("pai");
        canOperateBote.add("mae");
        canOperateBote.add("policial");

        return canOperateBote;
    }


    private static Set<String> populatePeopleToCross(){
        Set<String> leftSide = new HashSet<>();

        leftSide.add("pai");
        leftSide.add("mae");
        leftSide.add("filho1");
        leftSide.add("filho2");
        leftSide.add("filha1");
        leftSide.add("filha2");
        leftSide.add("policial");
        leftSide.add("ladrao");

        return leftSide;
    }
}

//O bote carrega duas pessoas por vez
//Apenas a mãe, o pai e o policial pode operar o bote
//A mãe não pode ser deixada sozinha com os filhos
//O pai não pode ser deixado sozinho com as filhas
//O ladrão não pode ser deixado sozinho sem o policial
//Mãe
//Pai
//Filhos
//Filhos
//Filhas
//Filhas
//Policial
//Ladrão