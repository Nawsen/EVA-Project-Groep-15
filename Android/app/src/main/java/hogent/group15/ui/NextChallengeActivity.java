package hogent.group15.ui;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.animation.AnimationUtils;

import java.net.URI;

import hogent.group15.Challenge;
import hogent.group15.ui.util.ActionBarConfig;

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

        firstChallenge.setAlpha(0f);
        secondChallenge.setAlpha(0f);
        thirdChallenge.setAlpha(0f);

        addTestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return ActionBarConfig.onCreateOptionsMenu(menu, this);
    }

    private void addTestData() {
        firstChallenge.updateContents(new Challenge(URI.create("http://www.evavzw.be/sites/default/files/styles/header_image/public/recipe/header/congiglie.jpg?itok=cql-paDx"), "Maak Winterse conchiglie met veggiegehakt, boerenkool en pompoensaus", "Kooktijd: Middel\nMoeilijkheidsgraad: Voor starters", "Kook de pastaschelpen in gezouten water, giet af en laat ze uitlekken op een keukenhanddoek.\n" +
                "Warm de oven voor op 180°C.\n" +
                "Snijd voor de pompoensaus de pompoen in blokjes en de preiwitten en de ui in kleine stukjes.\n" +
                "Verhit wat olijfolie in een pot en bak de groenten goudbruin. Voeg de gladde mosterd toe en laat even mee bakken. Blus met een scheut witte wijn. Giet de groentebouillon erbij tot de groenten net onder staan en laat helemaal gaar koken. Pureer de groenten met een mixer tot een gladde saus en meng er het citroensap en de graantjesmosterd onder. Breng op smaak met zout.\n" +
                "Snijd de champignons in kleine blokjes en versnipper de sjalotjes.\n" +
                "Zet een bakpan op het vuur. Bak de sjalot aan, voeg de champignons toe en bak ze verder kort op hoog vuur. Blus met een scheutje witte wijn en laat dit helemaal verdampen. Neem van het vuur en kruid ze met wat zout.\n" +
                "Was de boerenkool, verwijder de dikke nerven en snijd ze fijn. Verhit wat olijfolie in een wok en roerbak hierin de boerenkool. Meng hieronder de linzen en champignons.\n" +
                "Bestrijk de bodem van een ovenschaal met de pompoensaus. Vul de pastaschelpen met de vulling en leg ze in de schaal. Verdeel er hier en daar nog wat pompoensaus over en schuif de schotel in de oven voor een tiental minuten.\n" +
                "Werk af met stukjes geroosterde hazelnoten.", 10), new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(firstChallenge, "alpha", 0f, 1f).start();
            }
        });
        secondChallenge.updateContents(new Challenge(URI.create("http://www.evavzw.be/sites/default/files/styles/header_image/public/recipe/header/Salade%20van%20babyspinazie_1.jpg?itok=XWZCLzaj"), "Maak een Vegan Vegatable Pad Thai", "A description of the second challenge", "The detailed description of the second challenge", 12), new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(secondChallenge, "alpha", 0f, 1f).start();
            }
        });
        thirdChallenge.updateContents(new Challenge(URI.create("http://www.evavzw.be/sites/default/files/styles/header_image/public/term_description/images/10700567_763958877010844_19200954128799604_o.jpg?itok=OJnhJpWw"), "Ga naar restaurant X", "A description of the third challenge", "The detailed description of the third challenge", 15), new Runnable() {
            @Override
            public void run() {
                ObjectAnimator.ofFloat(thirdChallenge, "alpha", 0f, 1f).start();
            }
        });
    }
}
