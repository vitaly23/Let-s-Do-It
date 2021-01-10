import { ItemId } from './item-id';
import { CreatedBy } from './created-by';
import { Location } from './location';

export interface ItemBoundary {
    itemId: ItemId,
    type: string,
    name: string,
    active: boolean;
    createdTimestamp?: Date;
    createdBy: CreatedBy,
    location: Location,
    itemAttributes: Map<String, Object>
}