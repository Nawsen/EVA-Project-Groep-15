package hogent.group15.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URI;

import hogent.group15.Challenge;

public class NextChallengeActivity extends AppCompatActivity {

    private ChallengeView firstChallenge;
    private ChallengeView secondChallenge;
    private ChallengeView thirdChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_next);
        firstChallenge = (ChallengeView) findViewById(R.id.first_challenge);
        secondChallenge = (ChallengeView) findViewById(R.id.second_challenge);
        thirdChallenge = (ChallengeView) findViewById(R.id.third_challenge);
        addTestData();
    }

    private void addTestData() {
        URI uriFirst = URI.create("http://www.evavzw.be/sites/default/files/styles/recipe_thumbnail/public/recipe/teaser/congiglie.jpg?itok=UyECvLv_");

        firstChallenge.updateContents(new Challenge(uriFirst, "Maak Winterse conchiglie met veggiegehakt, boerenkool en pompoensaus", "Kooktijd: Middel\nMoeilijkheidsgraad: Voor starters", "Kook de pastaschelpen in gezouten water, giet af en laat ze uitlekken op een keukenhanddoek.\n" +
                "Warm de oven voor op 180°C.\n" +
                "Snijd voor de pompoensaus de pompoen in blokjes en de preiwitten en de ui in kleine stukjes.\n" +
                "Verhit wat olijfolie in een pot en bak de groenten goudbruin. Voeg de gladde mosterd toe en laat even mee bakken. Blus met een scheut witte wijn. Giet de groentebouillon erbij tot de groenten net onder staan en laat helemaal gaar koken. Pureer de groenten met een mixer tot een gladde saus en meng er het citroensap en de graantjesmosterd onder. Breng op smaak met zout.\n" +
                "Snijd de champignons in kleine blokjes en versnipper de sjalotjes.\n" +
                "Zet een bakpan op het vuur. Bak de sjalot aan, voeg de champignons toe en bak ze verder kort op hoog vuur. Blus met een scheutje witte wijn en laat dit helemaal verdampen. Neem van het vuur en kruid ze met wat zout.\n" +
                "Was de boerenkool, verwijder de dikke nerven en snijd ze fijn. Verhit wat olijfolie in een wok en roerbak hierin de boerenkool. Meng hieronder de linzen en champignons.\n" +
                "Bestrijk de bodem van een ovenschaal met de pompoensaus. Vul de pastaschelpen met de vulling en leg ze in de schaal. Verdeel er hier en daar nog wat pompoensaus over en schuif de schotel in de oven voor een tiental minuten.\n" +
                "Werk af met stukjes geroosterde hazelnoten.", 10));
        secondChallenge.updateContents(new Challenge(uriFirst, "Second challenge", "A description of the second challenge", "The detailed description of the second challenge", 12));
        thirdChallenge.updateContents(new Challenge(uriFirst, "Third challenge", "A description of the third challenge", "The detailed description of the third challenge", 15));
    }
}
