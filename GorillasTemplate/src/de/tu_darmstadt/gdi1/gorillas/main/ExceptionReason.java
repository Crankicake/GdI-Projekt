package de.tu_darmstadt.gdi1.gorillas.main;

public enum ExceptionReason {
	Undefined,
	ThrowAttemptAngelWrong,
	ThrowAttemptVelocityWrong,
	ThrowAttemptGravityNegative,
	ThrowAttemptPositionOutsideWindow,
	ThrowAttemptNoNextPosition,
	StateHasWrongID,
	UnknownPlayerID
	// Hier sind die 4 hinzu gekommen, damit man wei�, wo und warum. Name ist ja selbsterkl�rend.
}
