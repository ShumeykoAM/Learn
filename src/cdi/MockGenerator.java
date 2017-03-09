package cdi;

import javax.enterprise.inject.Alternative;

/**
 * @author sbt-shumeyko-am
 * @ created 09.03.2017
 * @ $Author$
 * @ $Revision$
 */
@Alternative
@ThirteenDigits
public class MockGenerator
    implements NumberGenerator
{
    @Override
    public int generateNumber()
    {
        return 0;
    }
}
