using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Common
{
    public class PetFilter
    {
        private Guid? ownerId = null;

        public Guid? OwnerId { get => ownerId; set => ownerId = value; }
    }
}
