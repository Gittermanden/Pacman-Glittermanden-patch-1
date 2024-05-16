package umlclasses;
public enum Gamemodes {
        Play(1.0,1.0),
        Power(2.0,1.25),
        Pause(1.0,0.0),
        PassedOn(1.0,1.0);

        private double scoreMultiplier;
        private double speedMultiplier;
        Gamemodes(double scoreMultiplier,double speedMultiplier) {
            this.scoreMultiplier = scoreMultiplier;
            this.speedMultiplier = speedMultiplier;
        }
    
    
        public double getSpeedMultiplier() {
            return speedMultiplier;
        }
        public double getScoreMultiplier() {
            return scoreMultiplier;
        }    
    }

