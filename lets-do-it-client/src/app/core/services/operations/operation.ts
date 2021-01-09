import { OperationId } from './operation-id';
import { Item } from './item';
import { InvokedBy } from './invoked-by';

export interface Operation {
    operationId: OperationId;
    type: string;
    item: Item;
    createdTimeStamp?: Date;
    invokedBy: InvokedBy;
    operationAttributes: Map<string, Object>;
}