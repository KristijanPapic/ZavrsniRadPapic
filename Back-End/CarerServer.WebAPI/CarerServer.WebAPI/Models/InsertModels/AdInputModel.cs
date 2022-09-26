using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CarerServer.WebAPI.Models.InsertModels
{
    public class AdInputModel
    {
            private int type;
            private string job;
            private string description;
            private Guid petId;
            private Guid userId;

            public int Type { get => type; set => type = value; }
            public string Job { get => job; set => job = value; }
            public string Description { get => description; set => description = value; }
            public Guid PetId { get => petId; set => petId = value; }
            public Guid UserId { get => userId; set => userId = value; }
    }
}
