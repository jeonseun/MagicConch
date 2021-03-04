package team.univ.magic_conch.visibility;

import org.springframework.stereotype.Component;

@Component
public class BundleVisibilityChecker implements VisibilityChecker {

    @Override
    public boolean isVisible(Visibility visibility, UserRelation relation) {
        boolean result = false;
        switch (visibility) {
            case PRIVATE:
                if (relation == UserRelation.MY_SELF) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case PUBLIC:
                result = true;
                break;
            case FRIEND:
                if (relation == UserRelation.FRIEND || relation == UserRelation.MY_SELF) {
                    result = true;
                } else {
                    result = false;
                }
                break;
        }
        return result;
    }
}
